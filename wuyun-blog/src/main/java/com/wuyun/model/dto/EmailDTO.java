package com.wuyun.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * 邮箱DTO
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
@Data
@ApiModel(description = "邮箱DTO")
public class EmailDTO {

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;
}
