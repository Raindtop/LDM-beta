package com.raindrop.security;

import com.raindrop.common.Model.Role.RoleManager;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.logging.Level;

@Configuration
@Log
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Reference
    private RoleManager roleManager;

    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("hasPermission2");
        return false;
    }
}
