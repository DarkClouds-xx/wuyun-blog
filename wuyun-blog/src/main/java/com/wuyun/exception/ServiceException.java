package com.wuyun.exception;

import lombok.Getter;

import static com.wuyun.enums.StatusCodeEnum.FAIL;


/**
 * 业务异常
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Getter
public final class ServiceException extends RuntimeException {

    /**
     * 返回失败状态码
     */
    private Integer code = FAIL.getCode();

    /**
     * 返回信息
     */
    private final String message;

    public ServiceException(String message) {
        this.message = message;
    }

}