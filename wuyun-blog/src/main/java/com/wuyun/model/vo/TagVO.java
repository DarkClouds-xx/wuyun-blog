package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 标签VO
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Data
@ApiModel(description = "标签VO")
public class TagVO {

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

    /**
     * 文章数量
     */
    @ApiModelProperty(value = "文章数量")
    private Integer articleCount;
}