package com.wuyun.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * Git信息
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Data
@ApiModel(description = "Git信息")
public class GitDTO {

    /**
     * code
     */
    @NotBlank(message = "code不能为空")
    @ApiModelProperty(value = "code")
    private String code;
}