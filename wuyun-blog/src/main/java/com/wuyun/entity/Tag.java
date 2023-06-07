package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 标签
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_tag")
public class Tag {

    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

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
