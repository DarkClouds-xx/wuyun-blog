package com.wuyun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

/**
 * knife4j配置
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {
    @Bean
    public Docket createRestApi() {
        //文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Collections.singleton("https"))
                .host("darkx.cloud")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wuyun.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("博客API文档")
                .description("基于SpringBoot + Vue开发的博客项目")
                .termsOfServiceUrl("darkx.cloud/api")
                .contact(new Contact("乌云", "https://github.com/DarkClouds-xx", "1404359447@qq.com"))
                .version("1.0")
                .build();
    }
}
