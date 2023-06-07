package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 相册
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_album")
public class Album {

    /**
     * 相册id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册封面
     */
    private String albumCover;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 状态 (1公开 2私密)
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