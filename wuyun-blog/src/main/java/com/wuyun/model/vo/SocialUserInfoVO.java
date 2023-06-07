package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


/**
 * 第三方账号信息
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Data
@Builder
@ApiModel(description = "第三方账号信息")
public class SocialUserInfoVO {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
}