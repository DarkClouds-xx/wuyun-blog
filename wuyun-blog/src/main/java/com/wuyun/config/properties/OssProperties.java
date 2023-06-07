package com.wuyun.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * oss配置属性
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "upload.oss")
public class OssProperties {

    /**
     * oss域名
     */
    private String url;

    /**
     * 终点
     */
    private String endpoint;

    /**
     * 访问密钥id
     */
    private String accessKeyId;

    /**
     * 访问密钥密码
     */
    private String accessKeySecret;

    /**
     * bucket名称
     */
    private String bucketName;
}