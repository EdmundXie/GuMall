package com.edm.gumall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients
public class GumallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(GumallWareApplication.class, args);
    }

}
