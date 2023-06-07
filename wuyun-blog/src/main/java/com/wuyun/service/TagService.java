package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Tag;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.TagDTO;
import com.wuyun.model.vo.*;

import java.util.List;

/**
 * 标签业务接口
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
public interface TagService extends IService<Tag> {

    /**
     * 查看后台标签列表
     *
     * @param conditionDTO 条件
     * @return 后台标签列表
     */
    PageResult<TagBackVO> listTagBackVO(ConditionDTO conditionDTO);

    /**
     * 添加标签
     *
     * @param tagDTO 标签
     */
    void addTag(TagDTO tagDTO);

    /**
     * 修改标签
     *
     * @param tag 标签
     */
    void updateTag(TagDTO tag);

    /**
     * 删除标签
     *
     * @param tagIdList 标签id集合
     */
    void deleteTag(List<Integer> tagIdList);

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    List<TagOptionVO> listTagOption();

    /**
     * 查看标签列表
     *
     * @return 标签列表
     */
    List<TagVO> listTagVO();

    /**
     * 查看标签下的文章
     * @param condition 条件
     * @return 文章列表
     */
    ArticleConditionList listArticleTag(ConditionDTO condition);
}
