package com.wuyun.model.vo;

import lombok.Data;


/**
 * QQ token信息
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Data
public class QqTokenVO {

    /**
     * openid
     */
    private String openid;

    /**
     * 客户端id
     */
    private String client_id;
}