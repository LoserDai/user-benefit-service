package com.benefit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Allen
 * @date 2025/6/4 17:11
 */
@SpringBootApplication
@MapperScan("com.benefit.mapper")
public class App {
    public static void main(String[] args){
        SpringApplication.run(App.class);
    }
}
