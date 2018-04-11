package com.yi.d1.controller;

import com.yi.d1.service.D2FeignService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MyController {
    private final D2FeignService d2FeignService;

    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;
    @Value("${foo.name:nothing}")
    private String foo;

    public MyController(D2FeignService d2FeignService) {
        this.d2FeignService = d2FeignService;
    }

    @GetMapping("/")
    public String index() {
        return "Hello,i'm " + name + "," + age + " years old.\r\nAnd " + foo + " \r\n And from d2 index: " + d2FeignService.index();
    }

    @GetMapping("/d2User")
    public String user() {
        return d2FeignService.userList();
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World!";
    }
}