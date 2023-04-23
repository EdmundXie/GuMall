package com.edm.gumall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Projectname: gumall
 * @Filename: MyBatisConfig
 * @Author: EdmundXie
 * @Data:2023/4/21 13:02
 * @Email: 609031809@qq.com
 * @Description:
 */

@Configuration
@EnableTransactionManagement
@MapperScan("com.edm.gumall.product.dao")
public class MyBatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(true).setLimit(1000);
        return paginationInterceptor;
    }
}
