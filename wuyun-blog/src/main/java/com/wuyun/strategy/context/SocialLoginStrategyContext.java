package com.wuyun.strategy.context;

import com.wuyun.enums.LoginTypeEnum;
import com.wuyun.strategy.SocialLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 登录策略上下文
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Service
@RequiredArgsConstructor
public class SocialLoginStrategyContext {

    private final Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    /**
     * 登录
     *
     * @param data          data
     * @param loginTypeEnum 登录枚举
     * @return {@link String} Token
     */
    public String executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }
}
