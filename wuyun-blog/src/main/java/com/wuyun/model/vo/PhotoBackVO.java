package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 后台照片VO
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Data
@ApiModel(description = "后台照片VO")
public class PhotoBackVO {

    /**
     * 照片id
     */
    @ApiModelProperty(value = "照片id")
    private Integer id;

    /**
     * 照片名
     */
    @ApiModelProperty(value = "照片名")
    private String photoName;

    /**
     * 照片描述
     */
    @ApiModelProperty(value = "照片描述")
    private String photoDesc;

    /**
     * 照片地址
     */
    @ApiModelProperty(value = "照片地址")
    private String photoUrl;
}