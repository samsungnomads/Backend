package com.samsungnomads.wheretogo.domain.test;

import com.samsungnomads.wheretogo.global.response.SuccessCode;
import com.samsungnomads.wheretogo.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("123")
    public String test123() {
        return "Test Endpoint";
    }
}
