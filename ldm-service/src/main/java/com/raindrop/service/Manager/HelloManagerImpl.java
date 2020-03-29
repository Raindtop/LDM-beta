package com.raindrop.service.Manager;

import com.alibaba.dubbo.config.annotation.Service;
import com.raindrop.common.Model.Hello.HelloManager;

@Service
public class HelloManagerImpl implements HelloManager {

    @Override
    public void SayHello(String name) {
        System.out.println("Hello " + name);
    }
}
