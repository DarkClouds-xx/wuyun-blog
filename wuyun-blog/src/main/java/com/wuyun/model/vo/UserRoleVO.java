package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户角色VO
 *
 * @author DarkClouds
 * @date 2023/05/12
 */
@Data
@ApiModel(description = "用户角色VO")
public class UserRoleVO {

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
}