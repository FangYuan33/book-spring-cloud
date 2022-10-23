package com.cloud.mall.fy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/admin")
public class AdminUserController {

    @GetMapping("/test/{userId}")
    public String test(@PathVariable("userId") Long userId) {
        return "User: " + userId;
    }
}
