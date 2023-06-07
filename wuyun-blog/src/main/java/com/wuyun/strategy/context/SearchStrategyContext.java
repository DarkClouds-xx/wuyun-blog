package com.wuyun.strategy.context;

import com.wuyun.model.vo.ArticleSearchVO;
import com.wuyun.strategy.SearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.wuyun.enums.SearchModeEnum.getStrategy;


/**
 * 搜索策略上下文
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
@Service
@RequiredArgsConstructor
public class SearchStrategyContext {

    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    private final Map<String, SearchStrategy> searchStrategyMap;

    /**
     * 执行搜索策略
     *
     * @param keyword 关键字
     * @return {@link List <ArticleSearchVO>} 搜索文章
     */
    public List<ArticleSearchVO> executeSearchStrategy(String keyword) {
        return searchStrategyMap.get(getStrategy(searchMode)).searchArticle(keyword);
    }

}