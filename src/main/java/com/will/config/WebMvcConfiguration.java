package com.will.config;

import com.will.interceptors.ManageProtectedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: UserCenter
 * @description: config类
 * @author: Mr.Zhang
 * @create: 2025-03-27 16:19
 **/

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    ManageProtectedInterceptor manageProtectedInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(manageProtectedInterceptor).addPathPatterns("/user/manage/**");
    }
}
