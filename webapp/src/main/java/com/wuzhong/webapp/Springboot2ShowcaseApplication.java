package com.wuzhong.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Springboot2ShowcaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ShowcaseApplication.class, args);
    }

}

