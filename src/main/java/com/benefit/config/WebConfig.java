package com.benefit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Allen
 * @date 2025/6/26 11:25
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.image.storage.path}")
    private String imageStoragePath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射本地文件系统路径到URL
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageStoragePath)
                // 设置缓存时间 (秒)
                .setCachePeriod(3600);
    }
}