package com.wuyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.client.RestTemplate;

/**
 * 博客应用程序启动类
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@SpringBootApplication
@MapperScan(basePackages = "com.wuyun.mapper")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
