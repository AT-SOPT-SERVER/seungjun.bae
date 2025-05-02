package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum SuccessCode {
    CREATE_CONTENT(HttpStatus.CREATED , "게시글이 작성되었습니다."),
    GET_ALL_CONTENT(HttpStatus.OK,"전체 게시글이 조회되었습니다."),
    SEARCH_KEYWORD(HttpStatus.OK, "키워드로 게시글 검색 성공"),
    GET_SPECIFIC_CONTENT(HttpStatus.OK, "게시글 상세 조회"),
    DELETE_CONTENT(HttpStatus.OK, "게시글이 삭제되었습니다."),
    UPDATE_CONTENT(HttpStatus.OK,"게시물이 수정되었습니다."),

    CREATE_USER(HttpStatus.CREATED, "회원가입이 정상적으로 완료되었습니다.")
    ;


    private final HttpStatus status;
    private final String message;

    SuccessCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
