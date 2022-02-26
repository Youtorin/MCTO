package com.yangdonglin.mcto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class CrossConfig extends WebMvcConfigurerAdapter {

    @Value("${cross.origin1:https://localhost:3000}")
    private String origin1;

    /**********************************
     * 用途说明: 添加跨域配置
     * 参数说明 registry
     * 返回值说明:
     ***********************************/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origin1)
                .allowedMethods("*")
                .allowedHeaders("*")
                //允许跨域传cookie
                .allowCredentials(true)
                .maxAge(3600);
    }
}
