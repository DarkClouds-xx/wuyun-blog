package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.VisitLog;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.PageResult;

/**
 * 访问日志业务接口
 *
 * @author DarkClouds
 * @date 2023/05/19
 */
public interface VisitLogService extends IService<VisitLog> {

    /**
     * 保存访问日志
     *
     * @param visitLog 访问日志信息
     */
    void saveVisitLog(VisitLog visitLog);

    /**
     * 查看访问日志列表
     *
     * @param condition 条件
     * @return 日志列表
     */
    PageResult<VisitLog> listVisitLog(ConditionDTO condition);
}
