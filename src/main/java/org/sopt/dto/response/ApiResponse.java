package org.sopt.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int status;
    private String message;
    private T result;

    public ApiResponse(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
