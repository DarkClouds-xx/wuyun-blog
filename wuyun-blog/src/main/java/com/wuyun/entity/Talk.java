package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 说说
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_talk")
public class Talk {

    /**
     * 说说id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 说说内容
     */
    private String talkContent;

    /**
     * 说说图片
     */
    private String images;

    /**
     * 是否置顶 (0否 1是)
     */
    private Integer isTop;

    /**
     * 状态 (1公开  2私密)
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}