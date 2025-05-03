package com.samsungnomads.wheretogo.domain.test.controller;

import com.samsungnomads.wheretogo.domain.test.dto.AuthTestResponseDto;
import com.samsungnomads.wheretogo.domain.test.dto.TestResponseDto;
import com.samsungnomads.wheretogo.global.security.dto.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsungnomads.wheretogo.global.success.SuccessCode;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;

@Slf4j
@RestController
@RequestMapping("api/test")
public class TestController implements TestControllerDocs {

    @GetMapping("/success")
    public ResponseEntity<SuccessResponse> success() {
        return SuccessResponse.of(SuccessCode.OK);
    }

    @GetMapping("/success-with-data")
    public ResponseEntity<SuccessResponse<TestResponseDto>> successWithData() {
        TestResponseDto testResponseDto = new TestResponseDto("테스트 메시지 입니다", 123);
        return SuccessResponse.of(SuccessCode.OK, testResponseDto);
    }

    @GetMapping("/auth")
    public ResponseEntity<SuccessResponse<AuthTestResponseDto>> auth(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        AuthTestResponseDto authTestResponseDto = new AuthTestResponseDto(userDetails.getUsername(), userDetails.getAuthoritiesAsString());
        return SuccessResponse.of(SuccessCode.OK, authTestResponseDto);
    }

    @GetMapping("123")
    public String test123() {
        return "Test Endpoint";
    }
}
