package com.samsungnomads.wheretogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
    public class WhereToGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhereToGoApplication.class, args);
    }

}
