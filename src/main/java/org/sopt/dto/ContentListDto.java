package org.sopt.dto;

import org.sopt.domain.Post;

import java.time.LocalDateTime;

public record ContentListDto (String title, Long userId, LocalDateTime postTime){
    public ContentListDto(Post post){
        this(post.getTitle(), post.getUser().getId(), post.getPostTime());
    }
}
