package com.wuyun.service;

import com.wuyun.model.vo.BlogBackInfoVO;
import com.wuyun.model.vo.BlogInfoVO;

/**
 * 博客业务接口
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
public interface BlogInfoService {
    /**
     * 上传访客信息
     */
    void report();

    /**
     * 查看博客信息
     *
     * @return 博客信息
     */
    BlogInfoVO getBlogInfo();

    /**
     * 查看博客后台信息
     *
     * @return 博客后台信息
     */
    BlogBackInfoVO getBlogBackInfo();

}
