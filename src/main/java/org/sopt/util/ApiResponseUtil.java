package org.sopt.util;

import org.sopt.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status, String message, T result){
        ApiResponse<T> response = new ApiResponse<>(status, message, result);
        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> failure(HttpStatus status, String message){
        ApiResponse<T> response = new ApiResponse<>(status, message);
        return ResponseEntity.status(status).body(response);
    }
}
