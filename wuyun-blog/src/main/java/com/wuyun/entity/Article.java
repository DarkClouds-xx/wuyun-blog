package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章
 *
 * @author DarkClouds
 * @date 2023/05/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_article")
public class Article {
    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章作者
     */
    private Integer userId;

    /**
     * 文章分类
     */
    private Integer categoryId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    private Integer articleType;

    /**
     * 是否置顶 (0否 1是)
     */
    private Integer isTop;

    /**
     * 是否删除 (0否 1是)
     */
    private Integer isDelete;

    /**
     * 是否推荐 (0否 1是)
     */
    private Integer isRecommend;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    private Integer status;

    /**
     * 发表时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
