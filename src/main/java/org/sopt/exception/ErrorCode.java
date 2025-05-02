package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    EMPTY_TITLE(HttpStatus.BAD_REQUEST, "제목을 입력해주세요"),
    LONG_TITLE(HttpStatus.BAD_REQUEST, "제목은 최대 30자까지 작성할 수 있어요"),
    EMPTY_BODY(HttpStatus.BAD_REQUEST, "내용을 입력해주세요"),
    LONG_BODY(HttpStatus.BAD_REQUEST, "내용은 최대 1,000자까지 작성할 수 있어요"),
    POST_TIME_RESTRICTION(HttpStatus.BAD_REQUEST, "게시글은 3분에 한 번씩만 작성할 수 있어요"),
    DUPLICATE_TITLE(HttpStatus.BAD_REQUEST, "같은 제목의 게시물이 이미 존재합니다."),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글 ID 입니다."),
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 키워드를 포함하는 게시물이 없습니다."),

    EMPTY_NAME(HttpStatus.BAD_REQUEST, "이름을 입력해주세요"),
    LONG_NAME(HttpStatus.BAD_REQUEST, "이름은 최대 10자입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID 입니다."),
    EMPTY_USER(HttpStatus.NOT_FOUND, "유저 ID를 입력해주세요.")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
