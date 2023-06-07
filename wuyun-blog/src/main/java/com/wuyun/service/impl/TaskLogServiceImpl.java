package com.wuyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.TaskLog;
import com.wuyun.mapper.TaskLogMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.TaskLogVO;
import com.wuyun.service.TaskLogService;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务日志业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Service
@RequiredArgsConstructor
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {

    private final TaskLogMapper taskLogMapper;

    @Override
    public PageResult<TaskLogVO> listTaskLog(ConditionDTO condition) {
        // 查询定时任务日志数量
        Long count = taskLogMapper.selectTaskLogCount(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询定时任务日志列表
        List<TaskLogVO> taskLogVOList = taskLogMapper.selectTaskLogVOList(PageUtils.getLimit(), PageUtils.getSize(), condition);
        return new PageResult<>(taskLogVOList, count);
    }

    @Override
    public void clearTaskLog() {
        taskLogMapper.delete(null);
    }
}
