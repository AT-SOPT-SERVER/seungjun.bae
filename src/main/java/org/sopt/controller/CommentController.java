package org.sopt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.CommentCreateRequest;
import org.sopt.dto.request.CommentUpdateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.CommentCreateResponse;
import org.sopt.dto.response.CommentReadResponse;
import org.sopt.dto.response.CommentUpdateResponse;
import org.sopt.exception.SuccessCode;
import org.sopt.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sopt.util.ApiResponseUtil.success;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<CommentCreateResponse>> createComment(@PathVariable Long postId, @RequestHeader Long userId, @RequestBody @Valid CommentCreateRequest request) {
        return success(SuccessCode.CREATE_COMMENT.getStatus(), SuccessCode.CREATE_COMMENT.getMessage(), commentService.createComment(request, postId, userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<CommentReadResponse>> getAllComment(@PathVariable Long postId) {
        return success(SuccessCode.GET_ALL_COMMENT.getStatus(), SuccessCode.GET_ALL_COMMENT.getMessage(), commentService.getAllComments(postId));
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentUpdateResponse>> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        return success(SuccessCode.UPDATE_COMMENT.getStatus(), SuccessCode.UPDATE_COMMENT.getMessage(), commentService.updateComment(request, commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return success(SuccessCode.DELETE_COMMENT.getStatus(), SuccessCode.DELETE_COMMENT.getMessage(), null);
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@PathVariable Long commentId, @RequestHeader Long userId){
        commentService.likeComment(commentId, userId);
        return success(SuccessCode.LIKE_COMMENT.getStatus(), SuccessCode.LIKE_COMMENT.getMessage(), null);
    }

    @DeleteMapping("/comments/{commentId}/unlike")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@PathVariable Long commentId, @RequestHeader Long userId){
        commentService.unlikeComment(commentId, userId);
        return success(SuccessCode.UNLIKE_COMMENT.getStatus(), SuccessCode.UNLIKE_COMMENT.getMessage(), null);
    }
}
