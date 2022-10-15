package com.example.nacosprovider.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoNacosAPI {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/serviceTest")
    public String serviceTest() {
        return "Demo nacos service test port:" + serverPort;
    }
}
