package com.wuyun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 回复数VO
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Data
@ApiModel(description = "回复数VO")
public class ReplyCountVO {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Integer commentId;

    /**
     * 回复数
     */
    @ApiModelProperty(value = "回复数")
    private Integer replyCount;
}
