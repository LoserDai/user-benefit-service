package com.benefit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Allen
 * @date 2025/6/6 14:53
 * @project swagger配置文件
 */
@Configuration // 声明配置类
@EnableOpenApi// 开启swagger
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()).enable(true)
                .select()
                //apis：扫描包路径，提取API接口
                //使用什么样的方式来扫描接口，RequestHandlerSelectors下共有五种方法可选。
                .apis(RequestHandlerSelectors.basePackage("com.benefit.controller"))
                //path：扫描接口的路径，PathSelectors下有四种方法，any()是全扫
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API信息基本配置
     * title：文档标题
     * description：文档描述
     * contacr：作者信息
     * version：版本
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot")
                .description("Spring Boot整合MongoDB、mybatisPlus、Swagger ")
                .contact(new Contact("Loser","目标网址","电子邮箱"))
                .version("v1.2")
                .build();
    }
}