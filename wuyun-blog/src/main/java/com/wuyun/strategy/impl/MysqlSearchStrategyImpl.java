package com.wuyun.strategy.impl;

import com.wuyun.mapper.ArticleMapper;
import com.wuyun.model.vo.ArticleSearchVO;
import com.wuyun.strategy.SearchStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuyun.constant.ElasticConstant.POST_TAG;
import static com.wuyun.constant.ElasticConstant.PRE_TAG;


/**
 * MySQL搜索策略
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
@Service("mySqlSearchStrategyImpl")
@RequiredArgsConstructor
public class MysqlSearchStrategyImpl implements SearchStrategy {

    private final ArticleMapper articleMapper;

    @Override
    public List<ArticleSearchVO> searchArticle(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return new ArrayList<>();
        }
        List<ArticleSearchVO> articleSearchVOList = articleMapper.searchArticle(keyword);
        return articleSearchVOList.stream()
                .map(article -> {
                    // 获取关键词第一次出现的位置
                    String articleContent = article.getArticleContent();
                    int index = article.getArticleContent().indexOf(keyword);
                    if (index != -1) {
                        // 获取关键词前面的文字
                        int preIndex = index > 25 ? index - 25 : 0;
                        String preText = article.getArticleContent().substring(preIndex, index);
                        // 获取关键词到后面的文字
                        int last = index + keyword.length();
                        int postLength = article.getArticleContent().length() - last;
                        int postIndex = postLength > 175 ? last + 175 : last + postLength;
                        String postText = article.getArticleContent().substring(index, postIndex);
                        // 文章内容高亮
                        articleContent = (preText + postText).replaceAll(keyword, PRE_TAG + keyword + POST_TAG);
                    }
                    // 文章标题高亮
                    String articleTitle = article.getArticleTitle().replaceAll(keyword, PRE_TAG + keyword + POST_TAG);
                    return ArticleSearchVO.builder()
                            .id(article.getId())
                            .articleTitle(articleTitle)
                            .articleContent(articleContent)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
