package com.raindrop.api;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@EnableDubbo
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@SpringBootApplication(scanBasePackages = {"com.raindrop.api", "com.raindrop.security", "com.raindrop.common"})
public class LdmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdmApiApplication.class, args);
    }
}


