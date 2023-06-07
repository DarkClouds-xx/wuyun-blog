package com.wuyun.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限信息
 *
 * @author DarkClouds
 * @date 2023/05/12
 */
@Data
@ApiModel(description = "权限信息")
public class RoleDTO {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private String id;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 是否禁用 (0否 1是)
     */
    @NotNull(message = "角色状态不能为空")
    @ApiModelProperty(value = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 菜单id集合
     */
    @ApiModelProperty(value = "菜单id集合")
    private List<Integer> menuIdList;

}
