package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.ExceptionLog;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.PageResult;


/**
 * 异常日志业务接口
 *
 * @author DarkClouds
 * @date 2023/05/19
 */
public interface ExceptionLogService extends IService<ExceptionLog> {

    /**
     * 查看异常日志列表
     *
     * @param condition 条件
     * @return 日志列表
     */
    PageResult<ExceptionLog> listExceptionLog(ConditionDTO condition);

    /**
     * 保存异常日志
     *
     * @param exceptionLog 异常日志信息
     */
    void saveExceptionLog(ExceptionLog exceptionLog);
}
