package com.wuyun.strategy.context;

import com.wuyun.enums.LikeTypeEnum;
import com.wuyun.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 点赞策略上下文
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Service
public class LikeStrategyContext {

    @Autowired
    private Map<String, LikeStrategy> likeStrategyMap;

    /**
     * 点赞
     *
     * @param likeType 点赞类型
     * @param typeId   类型id
     */
    public void executeLikeStrategy(LikeTypeEnum likeType, Integer typeId) {
        likeStrategyMap.get(likeType.getStrategy()).like(typeId);
    }
}