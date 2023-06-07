package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.OperationLog;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.OperationLogVO;
import com.wuyun.model.vo.PageResult;

/**
 * 操作日志业务接口
 *
 * @author DarkClouds
 * @date 2023/05/19
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查看操作日志列表
     *
     * @param condition 条件
     * @return 日志列表
     */
    PageResult<OperationLogVO> listOperationLogVO(ConditionDTO condition);

    /**
     * 保存操作日志
     *
     * @param operationLog 操作日志信息
     */
    void saveOperationLog(OperationLog operationLog);

}
