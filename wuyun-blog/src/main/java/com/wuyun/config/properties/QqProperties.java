package com.wuyun.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * QQ配置属性
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oauth.qq")
public class QqProperties {

    /**
     * QQ appId
     */
    private String appId;

    /**
     * 校验token地址
     */
    private String checkTokenUrl;

    /**
     * QQ用户信息地址
     */
    private String userInfoUrl;

}
