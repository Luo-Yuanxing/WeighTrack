package io.github.weightrack.config;

import io.github.weightrack.interceptor.LoginInterceptor;
import io.github.weightrack.interceptor.NoCacheInterceptor;
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
    @Bean
    public NoCacheInterceptor noCacheInterceptor() {
        return new NoCacheInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并指定拦截路径和排除路径
        // TODO 发布时开启登录拦截
//        registry.addInterceptor(loginInterceptor())
//                .addPathPatterns("/**")  // 拦截所有请求
//                .excludePathPatterns("/login", "/logon");

        // 注册no-cache 拦截器
        registry.addInterceptor(noCacheInterceptor())
                .addPathPatterns("/**");

    }

}
