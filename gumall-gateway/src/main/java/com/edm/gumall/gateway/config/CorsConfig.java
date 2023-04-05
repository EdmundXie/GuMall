package com.edm.gumall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

/**
 * @Projectname: gumall
 * @Filename: CrosConfig
 * @Author: EdmundXie
 * @Data:2023/4/4 12:41
 * @Email: 609031809@qq.com
 * @Description:
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        configurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(configurationSource);
    }
}
