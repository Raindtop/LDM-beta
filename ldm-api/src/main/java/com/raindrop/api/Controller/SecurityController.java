package com.raindrop.api.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.raindrop.common.Model.Account.Account;
import com.raindrop.common.Model.Account.AccountManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("test")
public class SecurityController {
    @Reference
    private AccountManager accountManager;

    @GetMapping("1")
    @PreAuthorize("hasPermission('ROLE_test','123123')")
    public void SayHello(@AuthenticationPrincipal Account account){
        System.out.println(accountManager);
        System.out.println(account);
        System.out.println("hello");
    }
}
