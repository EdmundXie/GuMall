package com.edm.gumall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GumallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GumallCouponApplication.class, args);
    }

}
