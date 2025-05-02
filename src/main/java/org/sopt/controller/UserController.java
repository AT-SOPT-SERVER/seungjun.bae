package org.sopt.controller;

import org.sopt.dto.request.UserCreateRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.UserCreateResponse;
import org.sopt.exception.SuccessCode;
import org.sopt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.util.ApiResponseUtil.success;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/signup")
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(@RequestBody UserCreateRequest request){
        return success(SuccessCode.CREATE_USER.getStatus(), SuccessCode.CREATE_USER.getMessage(), userService.createUser(request));
    }
}
