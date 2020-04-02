package com.raindrop.api.Controller;

import com.raindrop.common.Model.Account.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class SecurityController {

    @GetMapping("1")
    @PreAuthorize("hasRole('ROLE_test')")
    public void SayHello(@AuthenticationPrincipal Account account){
        System.out.println(account);
        System.out.println("hello");
    }
}
