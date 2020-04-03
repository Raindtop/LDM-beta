package com.raindrop.security;

import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;

public class MyDenyAllPermissionEvaluator extends DenyAllPermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        System.out.println("1111");
        return super.hasPermission(authentication, target, permission);
    }
}
