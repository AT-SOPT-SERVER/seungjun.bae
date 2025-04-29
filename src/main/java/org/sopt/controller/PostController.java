package org.sopt.controller;

import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
import org.sopt.exception.SuccessCode;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sopt.util.ApiResponseUtil.success;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createPost(@RequestBody ContentCreateRequest request) {
        return success(SuccessCode.CREATE_CONTENT.getStatus(), SuccessCode.CREATE_CONTENT.getMessage(), postService.createPost(request));
    }

    @GetMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentReadResponse>> getAllPosts(@RequestParam(required = false) String keyword){
        //키워드 검색기능
        if(keyword != null && !keyword.isBlank()){
            return success(SuccessCode.SEARCH_KEYWORD.getStatus(), SuccessCode.SEARCH_KEYWORD.getMessage(), postService.searchByKeyword(keyword));
        }
        //전체 게시글 조회
        return success(SuccessCode.GET_ALL_CONTENT.getStatus(), SuccessCode.GET_ALL_CONTENT.getMessage(), postService.getAllPosts());
    }

    @GetMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> getPostById(@PathVariable Long contentId){
        return success(SuccessCode.GET_SPECIFIC_CONTENT.getStatus(), SuccessCode.GET_SPECIFIC_CONTENT.getMessage(), postService.getIdPost(contentId));
    }

    @DeleteMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable Long contentId){
        postService.deleteIdPost(contentId);
        return success(SuccessCode.DELETE_CONTENT.getStatus(), SuccessCode.DELETE_CONTENT.getMessage(), null);
    }

    @PatchMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> updatePostTitle(@PathVariable Long contentId, @RequestBody ContentCreateRequest request){
        return success(SuccessCode.UPDATE_CONTENT.getStatus(),SuccessCode.UPDATE_CONTENT.getMessage(), postService.updateTitleById(contentId, request));
    }
}
