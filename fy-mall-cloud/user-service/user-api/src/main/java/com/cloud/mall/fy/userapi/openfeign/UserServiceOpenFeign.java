package com.cloud.mall.fy.userapi.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "user-service", path = "/user")
public interface UserServiceOpenFeign {

    @PostMapping("/admin/{token}")
    String getByToken(@PathVariable("token") String token);
}
