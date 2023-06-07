package com.wuyun.strategy;

import com.wuyun.model.vo.ArticleSearchVO;

import java.util.List;


/**
 * 文章搜索策略
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keyword 关键字
     * @return {@link List<ArticleSearchVO>} 文章列表
     */
    List<ArticleSearchVO> searchArticle(String keyword);
}
