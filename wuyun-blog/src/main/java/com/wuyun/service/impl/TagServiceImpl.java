package com.wuyun.service.impl;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.annotation.OptLogger;
import com.wuyun.entity.ArticleTag;
import com.wuyun.entity.Category;
import com.wuyun.entity.Tag;
import com.wuyun.exception.ServiceException;
import com.wuyun.mapper.ArticleMapper;
import com.wuyun.mapper.ArticleTagMapper;
import com.wuyun.mapper.TagMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.TagDTO;
import com.wuyun.model.vo.*;
import com.wuyun.service.TagService;
import com.wuyun.utils.BeanCopyUtils;
import com.wuyun.utils.PageUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

import static com.wuyun.constant.OptTypeConstant.DELETE;

/**
 * 标签业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final TagMapper tagMapper;

    private final ArticleTagMapper articleTagMapper;

    private final ArticleMapper articleMapper;

    @Override
    public PageResult<TagBackVO> listTagBackVO(ConditionDTO conditionDTO) {
        // 查询标签数量
        Long count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.hasText(conditionDTO.getKeyword()),
                        Tag::getTagName,
                        conditionDTO.getKeyword()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackVO> tagList = tagMapper.
                selectTagBackVO(PageUtils.getLimit(), PageUtils.getSize(), conditionDTO.getKeyword());
        return new PageResult<>(tagList, count);
    }

    @Override
    public void addTag(TagDTO tagDTO) {
        try {
            // 添加新标签
            Tag newTag = Tag.builder()
                    .tagName(tagDTO.getTagName())
                    .build();
            baseMapper.insert(newTag);
        } catch (Exception e) {
            throw new ServiceException("标签已存在");
        }
    }

    @Override
    public void updateTag(TagDTO tag) {
        try {
            // 修改标签
            Tag newTag = Tag.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .build();
            baseMapper.updateById(newTag);
        } catch (Exception e) {
            throw new ServiceException("标签已存在");
        }
    }

    @Override
    public void deleteTag(List<Integer> tagIdList) {
        // 标签下是否有文章
        Long count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, tagIdList));
        Assert.isFalse(count > 0, "删除失败，标签下存在文章");
        // 批量删除标签
        tagMapper.deleteBatchIds(tagIdList);
    }

    @Override
    public List<TagOptionVO> listTagOption() {
        // 查询分类
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId,Tag::getTagName)
                .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyBeanList(tagList, TagOptionVO.class);

    }

    @Override
    public List<TagVO> listTagVO() {
        return tagMapper.selectTagVOList();
    }

    @Override
    public ArticleConditionList listArticleTag(ConditionDTO condition) {
        List<ArticleConditionVO> articleConditionList = articleMapper.listArticleByCondition(PageUtils.getLimit(),
                PageUtils.getSize(), condition);
        String name = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                        .select(Tag::getTagName)
                        .eq(Tag::getId, condition.getTagId()))
                .getTagName();
        return ArticleConditionList.builder()
                .articleConditionVOList(articleConditionList)
                .name(name)
                .build();
    }
}
