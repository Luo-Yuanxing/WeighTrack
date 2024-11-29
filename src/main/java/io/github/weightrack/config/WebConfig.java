package io.github.weightrack.config;

import io.github.weightrack.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并指定拦截路径和排除路径
//        registry.addInterceptor(loginInterceptor())
//                .addPathPatterns("/**")  // 拦截所有请求
//                .excludePathPatterns("/login", "/logon");  // 排除登录和注册相关页面
    }

}
