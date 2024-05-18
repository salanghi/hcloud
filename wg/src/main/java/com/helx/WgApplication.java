package com.helx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WgApplication {

    public static void main(String[] args) {
        SpringApplication.run(WgApplication.class, args);
    }

}
