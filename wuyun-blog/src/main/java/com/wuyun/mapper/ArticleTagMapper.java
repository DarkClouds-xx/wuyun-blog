package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.ArticleTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章标签映射器
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    /**
     * 批量保存文章标签
     *
     * @param articleId 文章id
     * @param tagIdList 标签id列表
     */
    void saveBatchArticleTag(@Param("articleId") Integer articleId, List<Integer> tagIdList);
}
