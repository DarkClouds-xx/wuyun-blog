package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 菜单
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_menu")
public class Menu {

    /**
     * 菜单id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 类型（M目录 C菜单 B按钮）
     */
    private String menuType;

    /**
     * 名称
     */
    private String menuName;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 是否隐藏 (0否 1是)
     */
    private Integer isHidden;

    /**
     * 是否禁用 (0否 1是)
     */
    private Integer isDisable;

    /**
     * 排序
     */
    private Integer orderNum;

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