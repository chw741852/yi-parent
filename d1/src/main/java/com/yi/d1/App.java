package com.yi.d1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@EnableDiscoveryClient
public class App {
    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;
    @Value("${foo.name:nothing}")
    private String foo;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "hello,i'm " + name + "," + age + " years old.\nAnd " + foo;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World!";
    }
}
