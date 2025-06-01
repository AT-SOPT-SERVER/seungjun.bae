package org.sopt.dto.response;

import org.sopt.domain.Comment;

public record CommentInfo(Long id, String content, Long userId) {
    public static CommentInfo from(Comment comment) {
        return new CommentInfo(comment.getId(), comment.getContent(), comment.getUser().getId());
    }
}
