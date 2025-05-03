package com.samsungnomads.wheretogo.global.security.controller;

import com.samsungnomads.wheretogo.global.success.SuccessCode;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import com.samsungnomads.wheretogo.global.security.dto.LoginRequestDto;
import com.samsungnomads.wheretogo.global.security.dto.SignUpRequestDto;
import com.samsungnomads.wheretogo.global.security.jwt.JwtToken;
import com.samsungnomads.wheretogo.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return SuccessResponse.of(SuccessCode.MEMBER_SIGNUP_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtToken>> login(@RequestBody LoginRequestDto loginRequestDto) {

        JwtToken jwtToken = authService.login(loginRequestDto);
        return SuccessResponse.of(SuccessCode.MEMBER_LOGIN_SUCCESS, jwtToken);
    }


}
