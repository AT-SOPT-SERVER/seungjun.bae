package org.sopt.controller;

import org.sopt.dto.ContentDto;
import org.sopt.dto.ContentListDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.exception.SuccessCode;
import org.sopt.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createPost(@RequestHeader Long userId, @RequestBody ContentCreateRequest request) {
        return success(SuccessCode.CREATE_CONTENT.getStatus(), SuccessCode.CREATE_CONTENT.getMessage(), postService.createPost(request, userId));
    }

    @GetMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<Page<ContentListDto>>> getAllPosts(@PageableDefault(size=10, sort="postTime",direction = Sort.Direction.DESC) Pageable pageable, @RequestHeader(required = false) Long userId, @RequestParam(required = false) String keyword){
        //user기준 검색
        if(userId!=null){
            return success(SuccessCode.SEARCH_KEYWORD.getStatus(), SuccessCode.SEARCH_KEYWORD.getMessage(), postService.searchByUser(userId, pageable));
        }
        //키워드 검색
        if(keyword != null && !keyword.isBlank()){
            return success(SuccessCode.SEARCH_KEYWORD.getStatus(), SuccessCode.SEARCH_KEYWORD.getMessage(), postService.searchByKeyword(keyword, pageable));
        }
        //전체 게시글 조회
        return success(SuccessCode.GET_ALL_CONTENT.getStatus(), SuccessCode.GET_ALL_CONTENT.getMessage(), postService.getAllPosts(pageable));
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
