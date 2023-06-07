package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Category;
import com.wuyun.model.dto.CategoryDTO;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.*;

import java.util.List;


/**
 * 分类业务接口
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查看后台分类列表
     *
     * @param conditionDTO 查询条件
     * @return 后台分类列表
     */
    PageResult<CategoryBackVO> listCategoryBackVO(ConditionDTO conditionDTO);

    /**
     * 添加分类
     *
     * @param categoryDTO 类别dto
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 修改分类
     *
     * @param categoryDTO 分类dto
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     *
     * @param categoryIdList 分类id集合
     */
    void deleteCategory(List<Integer> categoryIdList);

    /**
     * 查看分类选项
     *
     * @return 分类列表
     */
    List<CategoryOptionVO> listCategoryOption();

    /**
     * 查看分类列表
     *
     * @return 分类列表
     */
    List<CategoryVO> listCategoryVO();


    /**
     * 查看分类下的文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticleConditionList listArticleCategory(ConditionDTO condition);
}
