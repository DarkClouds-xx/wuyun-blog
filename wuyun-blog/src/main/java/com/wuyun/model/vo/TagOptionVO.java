package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 标签选项VO
 *
 * @author DarkClouds
 * @date 2023/05/15
 */
@Data
@ApiModel(description = "标签选项VO")
public class TagOptionVO {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Integer id;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名")
    private String tagName;
}