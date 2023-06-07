package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.TaskLog;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.TaskLogVO;

/**
 * 定时任务日志业务接口
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
public interface TaskLogService extends IService<TaskLog> {

    /**
     * 查看后台定时任务日志
     *
     * @param condition 条件
     * @return 后台定时任务日志
     */
    PageResult<TaskLogVO> listTaskLog(ConditionDTO condition);

    /**
     * 清空定时任务日志
     */
    void clearTaskLog();
}
