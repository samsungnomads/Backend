package com.samsungnomads.wheretogo.domain.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController implements TestControllerDocs {

    @GetMapping("/success")
    public ResponseEntity<SuccessResponse> test() {
        return SuccessResponse.of(SuccessCode.OK);
    }

    @GetMapping("123")
    public String test123() {
        return "Test Endpoint";
    }
}
