package org.sopt.controller;

import org.sopt.dto.ContentDto;
import org.sopt.dto.request.ContentCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.ContentCreateResponse;
import org.sopt.dto.response.ContentReadResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sopt.util.ApiResponseUtil.failure;
import static org.sopt.util.ApiResponseUtil.success;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createPost(@RequestBody ContentCreateRequest request) {
        try {
            return success(HttpStatus.OK , "게시글이 작성되었습니다.", postService.createPost(request));
        } catch (Exception e) {
            return failure(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/api/v1/contents")
    public ResponseEntity<ApiResponse<ContentReadResponse>> getAllPosts(@RequestParam(required = false) String keyword){
        try{
            //키워드 검색기능
            if(keyword != null && !keyword.isBlank()){
                success(HttpStatus.OK, "키워드로 게시글 검색 성공", postService.searchByKeyword(keyword));
            }

            //전체 게시글 조회
            return success(HttpStatus.OK,"전체 게시글이 조회되었습니다.", postService.getAllPosts());
        } catch (Exception e) {
            return failure(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> getPostById(@PathVariable Long contentId){
        try{
            return success(HttpStatus.OK, "게시글 상세 조회", postService.getIdPost(contentId));
        }catch(Exception e){
            return failure(HttpStatus.NOT_FOUND, "사용자 정보를 읽어올 수 없습니다.");
        }
    }

    @DeleteMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable Long contentId){
        try{
            postService.deleteIdPost(contentId);
            return success(HttpStatus.OK, "게시글이 삭제되었습니다.", null);
        }catch (Exception e){
            return failure(HttpStatus.NOT_FOUND, "사용자 정보를 읽어올 수 없습니다.");
        }
    }

    @PatchMapping("/api/v1/contents/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> updatePostTitle(@PathVariable Long contentId, @RequestBody ContentCreateRequest request){
        try {
            return success(HttpStatus.OK,"게시물이 수정되었습니다.", postService.updateTitleById(contentId, request));
        } catch (Exception e){
            return failure(HttpStatus.NOT_FOUND, "사용자 정보를 읽어올 수 없습니다.");
        }
    }
}
