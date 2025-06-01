package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
import org.sopt.exception.SuccessCode;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sopt.util.ApiResponseUtil.success;

@RequestMapping("/api/v1/contents")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createPost(@RequestHeader Long userId, @RequestBody ContentCreateRequest request) {
        return success(SuccessCode.CREATE_CONTENT.getStatus(), SuccessCode.CREATE_CONTENT.getMessage(), postService.createPost(request, userId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ContentReadResponse>> getAllPosts(@RequestHeader(required = false) Long userId, @RequestParam(required = false) String keyword){
        //user기준 검색
        if(userId!=null){
            return success(SuccessCode.SEARCH_KEYWORD.getStatus(), SuccessCode.SEARCH_KEYWORD.getMessage(), postService.searchByUser(userId));
        }
        //키워드 검색
        if(keyword != null && !keyword.isBlank()){
            return success(SuccessCode.SEARCH_KEYWORD.getStatus(), SuccessCode.SEARCH_KEYWORD.getMessage(), postService.searchByKeyword(keyword));
        }
        //전체 게시글 조회
        return success(SuccessCode.GET_ALL_CONTENT.getStatus(), SuccessCode.GET_ALL_CONTENT.getMessage(), postService.getAllPosts());
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> getPostById(@PathVariable Long contentId){
        return success(SuccessCode.GET_SPECIFIC_CONTENT.getStatus(), SuccessCode.GET_SPECIFIC_CONTENT.getMessage(), postService.getIdPost(contentId));
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable Long contentId){
        postService.deleteIdPost(contentId);
        return success(SuccessCode.DELETE_CONTENT.getStatus(), SuccessCode.DELETE_CONTENT.getMessage(), null);
    }

    @PatchMapping("/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> updatePostTitle(@PathVariable Long contentId, @RequestBody ContentCreateRequest request){
        return success(SuccessCode.UPDATE_CONTENT.getStatus(),SuccessCode.UPDATE_CONTENT.getMessage(), postService.updateTitleById(contentId, request));
    }

    @PostMapping("/{contentId}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@PathVariable Long contentId, @RequestHeader Long userId){
        postService.likePost(contentId, userId);
        return success(SuccessCode.LIKE_CONTENT.getStatus(), SuccessCode.LIKE_CONTENT.getMessage(), null);
    }

    @DeleteMapping("/{contentId}/unlike")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@PathVariable Long contentId, @RequestHeader Long userId){
        postService.unlikePost(contentId, userId);
        return success(SuccessCode.UNLIKE_CONTENT.getStatus(), SuccessCode.UNLIKE_CONTENT.getMessage(), null);
    }
}
