package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Article;
import com.wuyun.model.dto.*;
import com.wuyun.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 文章业务接口
 *
 * @author DarkClouds
 * @date 2023/05/14
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查看后台文章列表
     *
     * @param condition 条件
     * @return 后台文章列表
     */
    PageResult<ArticleBackVO> listArticleBackVO(ConditionDTO condition);

    /**
     * 添加文章
     *
     * @param article 文章
     */
    void addArticle(ArticleDTO article);

    /**
     * 编辑文章
     *
     * @param articleId 文章id
     * @return 文章
     */
    ArticleInfoVO editArticle(Integer articleId);

    /**
     * 修改文章
     *
     * @param article 文章
     */
    void updateArticle(ArticleDTO article);

    /**
     * 删除文章
     *
     * @param articleIdList 文章id
     */
    void deleteArticle(List<Integer> articleIdList);

    /**
     * 回收或恢复文章
     *
     * @param delete 逻辑删除
     */
    void updateArticleDelete(DeleteDTO delete);

    /**
     * 上传文章图片
     *
     * @param file 文件
     * @return 文章图片地址
     */
    String saveArticleImages(MultipartFile file);

    /**
     * 修改文章置顶状态
     *
     * @param top 置顶
     */
    void updateArticleTop(TopDTO top);

    /**
     * 修改文章推荐状态
     *
     * @param recommend 推荐
     */
    void updateArticleRecommend(RecommendDTO recommend);

    /**
     * 查看首页文章列表
     *
     * @return 首页文章列表
     */
    PageResult<ArticleHomeVO> listArticleHomeVO();

    /**
     * 查看推荐文章
     *
     * @return 推荐文章
     */
    List<ArticleRecommendVO> listArticleRecommendVO();

    /**
     * 查看文章
     *
     * @param articleId 文章id
     * @return 文章
     */
    ArticleVO getArticleHomeById(Integer articleId);

    /**
     * 查看文章归档
     *
     * @return 文章归档
     */
    PageResult<ArchiveVO> listArchiveVO();

    /**
     * 搜索文章
     *
     * @param keyword 关键字
     * @return 文章列表
     */
    List<ArticleSearchVO> listArticlesBySearch(String keyword);

}
