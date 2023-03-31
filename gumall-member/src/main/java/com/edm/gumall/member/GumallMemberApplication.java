package com.edm.gumall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.edm.gumall.member.feign")
public class GumallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GumallMemberApplication.class, args);
    }

}
