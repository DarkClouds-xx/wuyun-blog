package com.wuyun.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * QQ登录DTO
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Data
public class QqLoginDTO {

    /**
     * openId
     */
    @NotBlank(message = "openId不能为空")
    private String openId;

    /**
     * accessToken
     */
    @NotBlank(message = "accessToken不能为空")
    private String accessToken;
}