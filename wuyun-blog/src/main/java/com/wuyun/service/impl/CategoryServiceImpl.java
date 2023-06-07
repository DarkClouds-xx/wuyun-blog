package com.wuyun.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.Article;
import com.wuyun.entity.Category;
import com.wuyun.exception.ServiceException;
import com.wuyun.mapper.ArticleMapper;
import com.wuyun.mapper.CategoryMapper;
import com.wuyun.model.dto.CategoryDTO;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.*;
import com.wuyun.service.CategoryService;
import com.wuyun.utils.BeanCopyUtils;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分类业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final ArticleMapper articleMapper;

    @Override
    public PageResult<CategoryBackVO> listCategoryBackVO(ConditionDTO conditionDTO) {
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.hasText(conditionDTO.getKeyword()),
                        Category::getCategoryName,
                        conditionDTO.getKeyword()));
        if (count == 0) {
            return new PageResult<>();
        }
        List<CategoryBackVO> categoryList = categoryMapper
                .selectCategoryBackVO(PageUtils.getLimit(), PageUtils.getSize(), conditionDTO.getKeyword());
        return new PageResult<>(categoryList, count);
    }


    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        try {
            //添加分类
            Category newCategory = Category.builder().categoryName(categoryDTO.getCategoryName()).build();
            categoryMapper.insert(newCategory);
        } catch (Exception e) {
            throw new ServiceException("分类已存在");
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        try {
            Category newCategory = Category.builder()
                    .id(categoryDTO.getId())
                    .categoryName(categoryDTO.getCategoryName())
                    .build();
            categoryMapper.updateById(newCategory);
        } catch (Exception e) {
            throw new ServiceException("分类已存在");
        }
    }

    @Override
    public void deleteCategory(List<Integer> categoryIdList) {
        // 分类下是否有文章
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryIdList));
        Assert.isFalse(count > 0, "删除失败，分类下存在文章");
        // 批量删除分类
        categoryMapper.deleteBatchIds(categoryIdList);
    }

    @Override
    public List<CategoryOptionVO> listCategoryOption() {
        // 查询分类
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                        .select(Category::getId,Category::getCategoryName)
                        .orderByDesc(Category::getId));
        return BeanCopyUtils.copyBeanList(categoryList, CategoryOptionVO.class);
    }

    @Override
    public List<CategoryVO> listCategoryVO() {
        return categoryMapper.selectCategoryVO();
    }

    @Override
    public ArticleConditionList listArticleCategory(ConditionDTO condition) {
        List<ArticleConditionVO> articleConditionList = articleMapper.listArticleByCondition(PageUtils.getLimit(),
                PageUtils.getSize(), condition);
        String name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                        .select(Category::getCategoryName)
                        .eq(Category::getId, condition.getCategoryId()))
                .getCategoryName();
        return ArticleConditionList.builder()
                .articleConditionVOList(articleConditionList)
                .name(name)
                .build();
    }
}
