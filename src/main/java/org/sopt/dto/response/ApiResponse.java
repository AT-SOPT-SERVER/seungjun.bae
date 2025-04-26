package org.sopt.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (HttpStatus status, String message, T result){
    public ApiResponse(HttpStatus status, String message){
        this(status, message, null);
    }
}
