package com.raindrop.api.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.raindrop.common.Model.Hello.HelloManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
    @Reference
    private HelloManager helloManager;

    @GetMapping("say")
    public void say(@RequestParam(value = "name", required = false) String name){
        System.out.println(helloManager);
        try{
            helloManager.SayHello("Jack");
        }catch (NullPointerException e){
            System.out.println("helloManager is null");
        }
    }
}
