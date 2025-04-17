package org.sopt.dto;

import org.sopt.domain.Post;

import java.util.List;

public class ContentDto {
    private long contentId;
    private String title;

    public ContentDto() {
    }

    public ContentDto(Post post) {
        this.contentId = post.getId();
        this.title = post.getTitle();
    }

    public long getContentId() {
        return contentId;
    }

    public String getTitle() {
        return title;
    }
}
