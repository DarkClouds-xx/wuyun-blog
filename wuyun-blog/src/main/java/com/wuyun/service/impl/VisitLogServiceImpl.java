package com.wuyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.VisitLog;
import com.wuyun.mapper.VisitLogMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.service.VisitLogService;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 访问日志业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/19
 */
@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

    private final VisitLogMapper visitLogMapper;

    @Override
    public void saveVisitLog(VisitLog visitLog) {
        // 保存访问日志
        visitLogMapper.insert(visitLog);
    }

    @Override
    public PageResult<VisitLog> listVisitLog(ConditionDTO condition) {
        // 查询访问日志数量
        Long count = visitLogMapper.selectCount(new LambdaQueryWrapper<VisitLog>()
                .like(StringUtils.hasText(condition.getKeyword()), VisitLog::getPage, condition.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询访问日志列表
        List<VisitLog> visitLogVOList = visitLogMapper.selectVisitLogList(PageUtils.getLimit(),
                PageUtils.getSize(), condition.getKeyword());
        return new PageResult<>(visitLogVOList, count);
    }

}
