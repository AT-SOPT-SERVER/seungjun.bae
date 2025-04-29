package org.sopt.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.sopt.domain.Post;
import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.InvalidRequestException;
import org.sopt.repository.PostRepository;
import org.sopt.util.PostValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private LocalDateTime latestPostTime = null;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ContentCreateResponse createPost(ContentCreateRequest request) {
        String title = request.title();

        //도배 방지기능
        PostValidator.postTime(latestPostTime);
        latestPostTime = LocalDateTime.now();

        //제목 유효성 검사
        PostValidator.titleLength(title);
        this.checkSameTitle(title);

        Post post = new Post(title);
        postRepository.save(post);
        return new ContentCreateResponse(post.getId());
    }

    public ContentReadResponse getAllPosts() {
        List<Post> rawContents = postRepository.findAll();
        List<ContentDto> contents = rawContents.stream().map(ContentDto::new).toList();
        return new ContentReadResponse(contents);
    }

    public ContentDto getIdPost(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new InvalidRequestException(ErrorCode.ID_NOT_FOUND));
        return new ContentDto(post);
    }

    public void deleteIdPost(long id){
        if (!postRepository.existsById(id)){
            throw new InvalidRequestException(ErrorCode.ID_NOT_FOUND);
        }
        postRepository.deleteById(id);
    }

    public ContentDto updateTitleById(Long id, ContentCreateRequest request){
        String newTitle = request.title();
        //제목 유효성검사
        PostValidator.titleLength(newTitle);
        this.checkSameTitle(newTitle);

        Post post = postRepository.findById(id).orElseThrow(() -> new InvalidRequestException(ErrorCode.ID_NOT_FOUND));
        post.setTitle(newTitle);
        return new ContentDto(post);
    }

    public ContentReadResponse searchByKeyword(String keyword){
        if(postRepository.findByTitleContaining(keyword).isEmpty()){
            throw new InvalidRequestException(ErrorCode.KEYWORD_NOT_FOUND);
        }
        return new ContentReadResponse(postRepository.findByTitleContaining(keyword).stream().map(ContentDto::new).toList());
    }

    public void checkSameTitle(String title){
        if(postRepository.existsByTitle(title)){
            throw new InvalidRequestException(ErrorCode.DUPLICATE_TITLE);
        }
    }

}
