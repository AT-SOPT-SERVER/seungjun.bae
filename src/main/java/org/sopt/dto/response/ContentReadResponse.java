package org.sopt.dto.response;

import org.sopt.domain.Post;
import org.sopt.dto.ContentDto;

import java.util.List;

public class ContentReadResponse {
    private List<ContentDto> contentList;

    public ContentReadResponse(){}

    public ContentReadResponse(List<ContentDto> contentList) {
        this.contentList = contentList;
    }

    public List<ContentDto> getContentList() {
        return contentList;
    }
}
