package com.helx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

    @GetMapping("/testSentinel")
    public Object testSentinel(){
        System.out.println("testSentinel");
        return "testSentinel";
    }
}
