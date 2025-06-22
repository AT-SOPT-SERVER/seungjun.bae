package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.ContentDto;
import org.sopt.dto.ContentListDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.InvalidRequestException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.util.Validator.PostValidator.isBodyValid;
import static org.sopt.util.Validator.PostValidator.isTitleValid;
import static org.sopt.util.Validator.UserValidator.isIdValid;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

//    private LocalDateTime latestPostTime = null;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ContentCreateResponse createPost(ContentCreateRequest request, Long userId) {
        String title = request.title();
        String body = request.body();

        //User가 있는지 검증
        isIdValid(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.USER_NOT_FOUND));

//        //도배 방지기능//개쳐비상 이거 잠깐 보류//이건 user별로 시간을 갖고 있어야될텐데
//        PostValidator.postTime(latestPostTime);
//        latestPostTime = LocalDateTime.now();

        isTitleValid(title);
        this.checkSameTitle(title);

        isBodyValid(body);

        Post post = new Post(title, body, user);
        postRepository.save(post);
        return new ContentCreateResponse(post.getId());
    }

    @Transactional(readOnly = true)
    public Page<ContentListDto> getAllPosts(Pageable pageable) {
        Page<Post> contents = postRepository.findAllByOrderByPostTimeDesc(pageable);
        return contents.map(ContentListDto::new);
    }

    @Transactional(readOnly = true)
    public ContentDto getIdPost(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new InvalidRequestException(ErrorCode.CONTENT_NOT_FOUND));
        return new ContentDto(post);
    }

    @Transactional
    public void deleteIdPost(long id){
        if (!postRepository.existsById(id)){
            throw new InvalidRequestException(ErrorCode.CONTENT_NOT_FOUND);
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public ContentDto updateTitleById(Long id, ContentCreateRequest request){
        String newTitle = request.title();
        String newBody = request.body();

        isTitleValid(newTitle);
        this.checkSameTitle(newTitle);
        isBodyValid(newBody);

        Post post = postRepository.findById(id).orElseThrow(() -> new InvalidRequestException(ErrorCode.CONTENT_NOT_FOUND));
        post.setTitle(newTitle);
        post.setBody(newBody);
        return new ContentDto(post);
    }

    @Transactional(readOnly = true)
    public Page<ContentListDto> searchByUser(Long userId, Pageable pageable){
        if(!userRepository.existsById(userId)){
            throw new InvalidRequestException(ErrorCode.USER_NOT_FOUND);
        }
        Page<Post> userPost = postRepository.findAllByUser_IdOrderByPostTimeDesc(userId, pageable);
        return userPost.map(ContentListDto::new);
    }

    @Transactional(readOnly = true)
    public Page<ContentListDto> searchByKeyword(String keyword, Pageable pageable){
        if(!postRepository.existsByTitleContaining(keyword)){
            throw new InvalidRequestException(ErrorCode.KEYWORD_NOT_FOUND);
        }
        Page<Post> keywordPost = postRepository.findAllByTitleContainingOrderByPostTimeDesc(keyword, pageable);
        return keywordPost.map(ContentListDto::new);
    }

    private void checkSameTitle(String title){
        if(postRepository.existsByTitle(title)){
            throw new InvalidRequestException(ErrorCode.DUPLICATE_TITLE);
        }
    }

}
