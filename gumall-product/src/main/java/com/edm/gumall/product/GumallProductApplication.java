package com.edm.gumall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Projectname: gumall
 * @Filename: GumallProductApplication
 * @Author: EdmundXie
 * @Data:2023/3/30 21:13
 * @Email: 609031809@qq.com
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.edm.gumall.product.feign")
public class GumallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(GumallProductApplication.class,args);
    }
}
