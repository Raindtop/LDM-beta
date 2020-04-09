package com.raindrop.security;

import com.raindrop.security.UserDetails.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * @Description TODO SpringSecurity的bean注解
 * @Author zhang hesong
 * @Date 17:01 2020/4/2
 **/
@Configuration
public class SecurityBeanConfig {
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(){
        return new MyWebSecurityConfigurerAdapter();
    }

    @Bean
    @ConditionalOnMissingBean(PermissionEvaluator.class)
    public PermissionEvaluator permissionEvaluator(){
        return new MyPermissionEvaluator();
    }
}
