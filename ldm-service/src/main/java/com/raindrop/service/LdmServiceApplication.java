package com.raindrop.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.raindrop.core.Mapper")
public class LdmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdmServiceApplication.class, args);
    }

}
