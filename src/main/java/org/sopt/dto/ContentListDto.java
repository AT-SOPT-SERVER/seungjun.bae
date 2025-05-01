package org.sopt.dto;

import org.sopt.domain.Post;

public record ContentListDto (String title, Long userId){
    public ContentListDto(Post post){
        this(post.getTitle(), post.getUser().getId());
    }
}
