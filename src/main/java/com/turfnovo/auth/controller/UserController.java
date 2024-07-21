package com.turfnovo.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turfnovo.auth.dto.request.LoginRequestDto;
import com.turfnovo.auth.dto.request.SignupRequestDto;
import com.turfnovo.auth.dto.request.UserUpdateDto;
import com.turfnovo.auth.dto.response.JwtResponseDto;
import com.turfnovo.auth.model.User;
import com.turfnovo.auth.service.UserService;
import com.turfnovo.global.ApiPathConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(ApiPathConstants.SIGN_IN)
    public ResponseEntity<JwtResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping(ApiPathConstants.SIGN_UP)
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest) {
        return ResponseEntity.ok(userService.createUser(signUpRequest));
    }

    @PutMapping(ApiPathConstants.UPDATE_USER)
    public ResponseEntity<User> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(authorization, userUpdateDto));
    }
}
