package biz.baijing.config;

import biz.baijing.interceptors.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfigure implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        log.info("webConfigeure 登录与否 {}", loginInterceptor);
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
    }


}
