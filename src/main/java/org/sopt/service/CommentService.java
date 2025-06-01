package org.sopt.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.Comment;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CommentCreateRequest;
import org.sopt.dto.request.CommentUpdateRequest;
import org.sopt.dto.response.CommentCreateResponse;
import org.sopt.dto.response.CommentInfo;
import org.sopt.dto.response.CommentReadResponse;
import org.sopt.dto.response.CommentUpdateResponse;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.InvalidRequestException;
import org.sopt.repository.CommentRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponse createComment(CommentCreateRequest request, Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new InvalidRequestException(ErrorCode.CONTENT_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException(ErrorCode.USER_NOT_FOUND));
        Comment comment = Comment.builder().content(request.content()).post(post).user(user).build();
        commentRepository.save(comment);
        return new CommentCreateResponse(comment.getId());
    }

    ;

    @Transactional(readOnly = true)
    public CommentReadResponse getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPost_Id(postId);
        List<CommentInfo> commentInfos = comments.stream().map(CommentInfo::from).toList();
        return new CommentReadResponse(commentInfos);
    }

    @Transactional
    public CommentUpdateResponse updateComment(CommentUpdateRequest request, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new InvalidRequestException(ErrorCode.CONTENT_NOT_FOUND));
        comment.setContent(request.content()); //dirty-checking이 있음: 엔티티 수정 후, 따로 업데이트 메서드 쓰지 않아도, transactional 안에 있으면 변경 감지 돼서 알아서 업데이트 쿼리 쏴줌
        return new CommentUpdateResponse(comment.getContent()); //수정된 댓글 반환.
    }

    @Transactional
    public void deleteComment(Long commentId) {
        //없는 아이디 조회하는 경우... 이러면 db 두번 접근해서 좀 비효율적이지 않나
        if (!commentRepository.existsById(commentId)) {
            throw new InvalidRequestException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(commentId);
    }
}
