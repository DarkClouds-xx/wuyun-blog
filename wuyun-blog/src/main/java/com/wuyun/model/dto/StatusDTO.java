package com.wuyun.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 状态DTO
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Data
@ApiModel(description = "状态DTO")
public class StatusDTO {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer status;
}
