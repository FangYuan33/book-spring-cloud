package com.example.nacosconsumer.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class DemoNacosConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumerTest")
    public String consumerTest() {
        // 这里借助Nacos注册中心，没有指定具体的地址而是通过服务名完成了请求调用
        String SERVICE_URL = "http://demo-nacos-provider";

        return restTemplate.getForObject(SERVICE_URL + "/serviceTest", String.class);
    }
}
