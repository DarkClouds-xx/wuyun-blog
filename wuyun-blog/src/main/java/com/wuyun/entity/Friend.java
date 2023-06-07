package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 友链
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Data
@TableName("b_friend")
public class Friend {

    /**
     * 友链id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 友链名称
     */
    private String name;

    /**
     * 友链颜色
     */
    private String color;

    /**
     * 友链头像
     */
    private String avatar;

    /**
     * 友链地址
     */
    private String url;

    /**
     * 友链介绍
     */
    private String introduction;

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