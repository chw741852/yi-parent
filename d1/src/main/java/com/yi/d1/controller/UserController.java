package com.yi.d1.controller;

import com.yi.d1.domain.User;
import com.yi.d1.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("save")
    public String save(User user) {
        userService.save(user);
        return "true";
    }

    @GetMapping("")
    public List<User> list() {
        return userService.findAll();
    }
}
