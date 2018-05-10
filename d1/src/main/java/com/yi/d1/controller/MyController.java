package com.yi.d1.controller;

import com.yi.d1.domain.User;
import com.yi.d1.service.D2FeignService;
import com.yi.logging.annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RefreshScope
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);
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

    @GetMapping("hello2")
    public String hello2() {
        return "aa";
    }

    @GetMapping("hello")
    @Log(api = "/hello", title = "测试Hello World", operate = Log.Operate.SELECT)
    public String hello(User user, HttpServletRequest request, String hello) {
        return "Hello d1";
    }

    @PostMapping("hello")
    @Log(title = "用户信息", operate = Log.Operate.SELECT, desc = "用户请求信息")
    public String hello3(User user) {
        return "hello world " + user.getName();
    }
}
