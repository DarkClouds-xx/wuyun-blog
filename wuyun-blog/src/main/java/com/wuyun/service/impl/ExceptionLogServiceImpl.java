package com.wuyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.ExceptionLog;
import com.wuyun.mapper.ExceptionLogMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.service.ExceptionLogService;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 异常日志业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/19
 */
@Service
@RequiredArgsConstructor
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    private final ExceptionLogMapper exceptionLogMapper;

    @Override
    public PageResult<ExceptionLog> listExceptionLog(ConditionDTO condition) {
        // 查询异常日志数量
        Long count = exceptionLogMapper.selectCount(new LambdaQueryWrapper<ExceptionLog>()
                .like(StringUtils.hasText(condition.getOptModule()), ExceptionLog::getModule, condition.getOptModule())
                .or()
                .like(StringUtils.hasText(condition.getKeyword()), ExceptionLog::getDescription, condition.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询异常日志列表
        List<ExceptionLog> operationLogVOList = exceptionLogMapper.selectExceptionLogList(PageUtils.getLimit(),
                PageUtils.getSize(), condition);
        return new PageResult<>(operationLogVOList, count);
    }

    @Override
    public void saveExceptionLog(ExceptionLog exceptionLog) {
        // 保存异常日志
        exceptionLogMapper.insert(exceptionLog);
    }
}
