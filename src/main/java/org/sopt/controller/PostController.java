package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    //생성자로 객체 만들어놓으면 스프링이 알아서 PostService라는 bean 찾아서 주입해줌
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createPost(@RequestBody ContentCreateRequest request) {
        try {
            ApiResponse<ContentCreateResponse> response = new ApiResponse<>(201, "게시글이 작성되었습니다.", postService.createPost(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<ContentCreateResponse> response = new ApiResponse<>(404, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentReadResponse>> getAllPosts(@RequestParam(required = false) String keyword){
        try{
            //키워드 검색기능
            if(keyword != null && !keyword.isBlank()){
                ApiResponse<ContentReadResponse> response= new ApiResponse<>(200, "키워드로 게시글 검색 성공", postService.searchByKeyword(keyword));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            //전체 게시글 조회
            List<Post> rawContents = postService.getAllPosts();
            List<ContentDto> contents = rawContents.stream().map(ContentDto::new).toList();
            ContentReadResponse result = new ContentReadResponse(contents);
            ApiResponse<ContentReadResponse> response = new ApiResponse<>(200,"전체 게시글이 조회되었습니다.", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<ContentReadResponse> response = new ApiResponse<>(404, "사용자 정보를 읽어올 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> getPostById(@PathVariable Long contentId){
        try{
            Post post = postService.getIdPost(contentId);
            ContentDto result = new ContentDto(post);
            ApiResponse<ContentDto> response = new ApiResponse<>(200, "게시글 상세 조회", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
            ApiResponse<ContentDto> response = new ApiResponse<>(404, "사용자 정보를 읽어올 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable Long contentId){
        try{
            postService.deleteIdPost(contentId);
            ApiResponse<Void> response = new ApiResponse<>(200, "게시글이 삭제되었습니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            ApiResponse<Void> response = new ApiResponse<>(404, "사용자 정보를 읽어올 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> updatePostTitle(@PathVariable Long contentId, @RequestBody ContentCreateRequest request){
        try {
            ApiResponse<ContentDto> response = new ApiResponse<>(200,"게시물이 수정되었습니다.", postService.updateTitleById(contentId, request));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            ApiResponse<ContentDto> response = new ApiResponse<>(404, "사용자 정보를 읽어올 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
