package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 照片VO
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Data
@ApiModel(description = "照片VO")
public class PhotoVO {

    /**
     * 照片id
     */
    @ApiModelProperty(value = "照片id")
    private Integer id;

    /**
     * 照片链接
     */
    @ApiModelProperty(value = "照片链接")
    private String photoUrl;
}