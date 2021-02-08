package com.wjh.blog.config;

import com.wjh.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {    //springboot2.0默认配置了


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")     //拦截路径
                .excludePathPatterns("/admin")    //拦截除去的路径
                .excludePathPatterns("/admin/login");
    }
}
