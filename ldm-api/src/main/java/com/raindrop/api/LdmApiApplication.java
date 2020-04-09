package com.raindrop.api;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.raindrop.common.Utils.RedisUtil;
import com.raindrop.security.MyPermissionEvaluator;
import com.raindrop.security.MyWebSecurityConfigurerAdapter;
import com.raindrop.security.UserDetails.UserDetailsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableDubbo
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@SpringBootApplication(scanBasePackages = {"com.raindrop.api", "com.raindrop.security", "com.raindrop.common"})
public class LdmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdmApiApplication.class, args);
    }
}


