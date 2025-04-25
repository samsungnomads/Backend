package com.samsungnomads.wheretogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 애플리케이션 메인 클래스
 * 🚀 애플리케이션의 진입점(entry point)
 */
@SpringBootApplication
@EntityScan(basePackages = "com.samsungnomads.wheretogo.domain")
public class WhereToGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhereToGoApplication.class, args);
    }
}
