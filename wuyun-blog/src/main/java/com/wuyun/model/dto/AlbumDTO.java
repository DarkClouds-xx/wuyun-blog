package com.wuyun.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 相册DTO
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Data
@ApiModel(description = "相册DTO")
public class AlbumDTO {

    /**
     * 相册id
     */
    @ApiModelProperty(value = "相册id")
    private Integer id;

    /**
     * 相册名
     */
    @NotBlank(message = "相册名不能为空")
    @ApiModelProperty(value = "相册名")
    private String albumName;

    /**
     * 相册描述
     */
    @ApiModelProperty(value = "相册描述")
    private String albumDesc;

    /**
     * 相册封面
     */
    @NotBlank(message = "相册封面不能为空")
    @ApiModelProperty(value = "相册封面")
    private String albumCover;

    /**
     * 状态 (1公开 2私密)
     */
    @ApiModelProperty(value = "状态 (1公开 2私密)")
    private Integer status;
}