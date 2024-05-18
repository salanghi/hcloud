package com.helx.usercenter.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class Controller {


    @Autowired
    ProducerService producerService;


    @GetMapping("/sendMsg")
    public Integer sendMsg(@RequestParam("msg") String msg){
        producerService.sendMessage("testTopic1","key",msg);
        return Response.SC_OK;
    }



}

