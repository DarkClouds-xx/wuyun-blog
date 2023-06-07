package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色VO
 *
 * @author DarkClouds
 * @date 2023/05/11
 */
@Data
@ApiModel(description = "角色VO")
public class RoleVO {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private String id;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 是否禁用 (0否 1是)
     */
    @ApiModelProperty(value = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;



}