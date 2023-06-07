package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 角色菜单
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_role_menu")
public class RoleMenu {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

}