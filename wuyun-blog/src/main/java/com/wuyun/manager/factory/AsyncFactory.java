package com.wuyun.manager.factory;

import com.wuyun.entity.ExceptionLog;
import com.wuyun.entity.OperationLog;
import com.wuyun.entity.VisitLog;
import com.wuyun.service.ExceptionLogService;
import com.wuyun.service.OperationLogService;
import com.wuyun.service.VisitLogService;
import com.wuyun.utils.SpringUtils;

import java.util.TimerTask;


/**
 * 异步工厂（产生任务用）
 *
 * @author DarkClouds
 * @date 2023/05/19
 */
public class AsyncFactory {

    /**
     * 记录操作日志
     *
     * @param operationLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOperation(OperationLog operationLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(OperationLogService.class).saveOperationLog(operationLog);
            }
        };
    }

    /**
     * 记录异常日志
     *
     * @param exceptionLog 异常日志信息
     * @return 任务task
     */
    public static TimerTask recordException(ExceptionLog exceptionLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(ExceptionLogService.class).saveExceptionLog(exceptionLog);
            }
        };
    }

    /**
     * 记录访问日志
     *
     * @param visitLog 访问日志信息
     * @return 任务task
     */
    public static TimerTask recordVisit(VisitLog visitLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(VisitLogService.class).saveVisitLog(visitLog);
            }
        };
    }
}