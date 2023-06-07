package com.wuyun.strategy;


/**
 * 点赞策略
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
public interface LikeStrategy {

    /**
     * 点赞
     *
     * @param typeId 类型id
     */
    void like(Integer typeId);
}
