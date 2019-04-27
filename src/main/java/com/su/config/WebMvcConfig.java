package com.su.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Springboot Web相关的配置属性设置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     *  配置静态资源目录 外部可以直接访问地址
     * */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 定义一个页面跳转
        registry.addViewController("/seller/index").setViewName("info/login");
        registry.addViewController("/seller/register-index").setViewName("info/register");
    }
}
