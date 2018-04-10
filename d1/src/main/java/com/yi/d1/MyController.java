package com.yi.d1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MyController {
    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;
    @Value("${foo.name:nothing}")
    private String foo;

    @GetMapping("/")
    public String index() {
        return "hello,i'm " + name + "," + age + " years old.\nAnd " + foo;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World!";
    }
}
