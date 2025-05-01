package org.sopt.dto;

import org.sopt.domain.Post;

public record ContentDto(String title, String body, String name) {
    public ContentDto(Post post) {
        this(post.getTitle(), post.getBody(), post.getUser().getName());
    }
}
