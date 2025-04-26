package org.sopt.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.sopt.domain.Post;
import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
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

        try{
            Post post = new Post(title);
            postRepository.save(post);
            return new ContentCreateResponse(post.getId());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("사용자 정보를 읽어올 수 없습니다.");
        }
    }

    public ContentReadResponse getAllPosts() {
        List<Post> rawContents = postRepository.findAll();
        List<ContentDto> contents = rawContents.stream().map(ContentDto::new).toList();
        return new ContentReadResponse(contents);
    }

    public ContentDto getIdPost(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return new ContentDto(post);
    }

    public void deleteIdPost(long id){
        if (!postRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        postRepository.deleteById(id);
    }

    public ContentDto updateTitleById(Long id, ContentCreateRequest request){
        String newTitle = request.title();
        //제목 유효성검사
        PostValidator.titleLength(newTitle);
        this.checkSameTitle(newTitle);

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        post.setTitle(newTitle);
        return new ContentDto(post);
    }

    public ContentReadResponse searchByKeyword(String keyword){
        if(postRepository.findByTitleContaining(keyword).isEmpty()){
            throw new EntityNotFoundException();
        }
        return new ContentReadResponse(postRepository.findByTitleContaining(keyword).stream().map(ContentDto::new).toList());
    }

    public void checkSameTitle(String title){
        if(postRepository.existsByTitle(title)){
            throw new IllegalArgumentException("같은 제목의 게시물이 이미 존재합니다.");
        }
    }

}
