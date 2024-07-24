package com.turfnovo.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turfnovo.ApiPathConstants;
import com.turfnovo.dto.request.user.LoginRequestDto;
import com.turfnovo.dto.request.user.SignupRequestDto;
import com.turfnovo.dto.request.user.UserUpdateDto;
import com.turfnovo.dto.response.user.JwtResponseDto;
import com.turfnovo.dto.response.user.UserResponseDto;
import com.turfnovo.model.user.User;
import com.turfnovo.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(ApiPathConstants.SIGN_IN)
    public JwtResponseDto authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping(ApiPathConstants.SIGN_UP)
    public User registerUser(@Valid @RequestBody SignupRequestDto signUpRequest) {
        return userService.createUser(signUpRequest);
    }

    @PutMapping(ApiPathConstants.UPDATE_USER)
    public User updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(authorization, userUpdateDto);
    }

    @GetMapping(ApiPathConstants.USER_GET_DETAILS)
    public UserResponseDto getMethodName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return userService.getUser(authorization);
    }
}
