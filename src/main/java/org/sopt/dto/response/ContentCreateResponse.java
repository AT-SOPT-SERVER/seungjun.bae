package org.sopt.dto.response;

public class ContentCreateResponse {
    private long contentId;

    public ContentCreateResponse(){}

    public ContentCreateResponse(long contendId) {
        this.contentId = contendId;
    }

    public long getContendId() {
        return contentId;
    }
}