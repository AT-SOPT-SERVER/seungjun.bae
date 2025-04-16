package org.sopt.dto.request;

public class ContentCreateRequest {
    private String title;

    public ContentCreateRequest(){

    }

    public ContentCreateRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
