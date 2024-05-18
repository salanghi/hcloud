package com.helx.usercenter.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cool", path = "/test")
public interface TestBak {

    @GetMapping("/sayHello")
    public String sayHello();
}
