package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 留言
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_message")
public class Message {

    /**
     * 留言id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户地址
     */
    private String ipSource;

    /**
     * 是否通过 (0否 1是)
     */
    private Integer isCheck;

    /**
     * 留言时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}