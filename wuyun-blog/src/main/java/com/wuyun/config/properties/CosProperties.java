package com.wuyun.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * cos配置属性
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.cos")
public class CosProperties {

    /**
     * cos域名
     */
    private String url;

    /**
     * 访问密钥id
     */
    private String secretId;

    /**
     * 访问密钥密码
     */
    private String secretKey;

    /**
     * 所属区域
     */
    private String region;

    /**
     * 存储桶名称
     */
    private String bucketName;
}