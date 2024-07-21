package com.hhplusconcert.config;

import com.hhplusconcert.interfaces.interceptor.QueueTokenCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final QueueTokenCheckInterceptor queueTokenCheckInterceptor;

    public WebConfig(QueueTokenCheckInterceptor queueTokenCheckInterceptor) {
        this.queueTokenCheckInterceptor = queueTokenCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(queueTokenCheckInterceptor)
                .addPathPatterns("/**");
    }
}
