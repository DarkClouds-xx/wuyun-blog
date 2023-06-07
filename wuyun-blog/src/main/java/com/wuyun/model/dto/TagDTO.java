package com.wuyun.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 标签DTO
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Data
@ApiModel(description = "标签DTO")
public class TagDTO {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空")
    @ApiModelProperty(value = "标签名")
    private String tagName;

}
