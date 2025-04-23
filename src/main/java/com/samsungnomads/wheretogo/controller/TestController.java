package com.samsungnomads.wheretogo.controller;

import com.samsungnomads.wheretogo.docs.TestControllerDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController implements TestControllerDocs {

    @GetMapping("")
    public String test() {
        return "Test Endpoint";
    }

    @GetMapping("123")
    public String test123() {
        return "Test Endpoint";
    }
}
