package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.Category;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.CategoryBackVO;
import com.wuyun.model.vo.CategoryOptionVO;
import com.wuyun.model.vo.CategoryVO;
import com.wuyun.model.vo.PageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类映射器
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询后台分类列表
     *
     * @param limit   页码
     * @param size    大小
     * @param keyword 关键字
     * @return 后台分类列表
     */
    List<CategoryBackVO> selectCategoryBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("keyword") String keyword);

    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    List<CategoryVO> selectCategoryVO();
}
