package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 文章标签
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_article_tag")
public class ArticleTag {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 标签id
     */
    private Integer tagId;

}