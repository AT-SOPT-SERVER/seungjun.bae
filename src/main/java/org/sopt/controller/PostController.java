package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
import org.sopt.service.PostService;
import org.sopt.validation.PostValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    //생성자로 객체 만들어놓으면 스프링이 알아서 PostService라는 bean 찾아서 주입해줌
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createPost(@RequestBody final ContentCreateRequest request) {
        try {
            //글자수 제한
            PostValidator.titleLength(request.getTitle());

            //생성
            Long newPostId = postService.createPost(request.getTitle());

            ContentCreateResponse result = new ContentCreateResponse(newPostId);
            ApiResponse<ContentCreateResponse> response = new ApiResponse<>(201, "게시글이 작성되었습니다.", result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<ContentCreateResponse> response = new ApiResponse<>(404, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("api/v1/contents")
    public ResponseEntity<ApiResponse<ContentReadResponse>> getAllPosts(){
        try{
            List<Post> rawContents = postService.getAllPosts();
            List<ContentDto> contents = rawContents.stream().map(ContentDto::new).toList();
            ContentReadResponse result = new ContentReadResponse(contents);
            ApiResponse<ContentReadResponse> response = new ApiResponse<>(200,"전체 게시글이 조회되었습니다.", result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<ContentReadResponse> response = new ApiResponse<>(404, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public Post getPostById(int postId){
        return postService.getIdPost(postId);
    }

    public Boolean deletePostById(int postId){
        return postService.deleteIdPost(postId);
    }

    public boolean updatePostTitle(int id, String newTitle){
        //제목 글자수 검사
        try {
            PostValidator.titleLength(newTitle);
            return postService.updateTitleById(id, newTitle);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Post> searchPostsByKeyword(String keyword){
        return postService.searchByKeyword(keyword);
    }
}
