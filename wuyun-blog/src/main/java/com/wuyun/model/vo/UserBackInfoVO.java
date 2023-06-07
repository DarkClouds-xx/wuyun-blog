package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * 后台登录用户信息
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Data
@Builder
@ApiModel(description = "后台登录用户信息")
public class UserBackInfoVO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private List<String> roleList;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private List<String> permissionList;

}