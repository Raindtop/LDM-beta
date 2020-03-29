package com.raindrop.api;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class LdmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdmApiApplication.class, args);
    }

}
