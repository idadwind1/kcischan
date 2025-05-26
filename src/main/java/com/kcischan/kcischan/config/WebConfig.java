package com.kcischan.kcischan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kcischan.kcischan.interceptor.AdminApisInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Autowired
  private AdminApisInterceptor adminApisInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(adminApisInterceptor)
        .addPathPatterns("/api/admin/**");
  }
}
