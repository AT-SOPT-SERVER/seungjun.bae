package org.sopt.dto;

import org.sopt.domain.Post;

public record ContentDto(long contentId, String title) {
    public ContentDto(Post post) {
        this(post.getId(), post.getTitle());
    }
}
