package com.wuyun.strategy;


/**
 * 第三方登录策略
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
public interface SocialLoginStrategy {

    /**
     * 登录
     *
     * @param data data
     * @return {@link String} Token
     */
    String login(String data);
}
