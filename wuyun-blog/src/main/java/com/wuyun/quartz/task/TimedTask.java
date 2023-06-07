package com.wuyun.quartz.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wuyun.mapper.VisitLogMapper;
import com.wuyun.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.wuyun.constant.RedisConstant.UNIQUE_VISITOR;


/**
 * 执行定时任务
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@SuppressWarnings(value = "all")
@Component("timedTask")
@RequiredArgsConstructor
public class TimedTask {

    private final RedisService redisService;

    private final VisitLogMapper visitLogMapper;

    /**
     * 清除博客访问记录
     */
    public void clear() {
        redisService.deleteObject(UNIQUE_VISITOR);
    }

    /**
     * 测试任务
     */
    public void test() {
        System.out.println("测试任务");
    }

    /**
     * 清除一周前的访问日志
     */
    public void clearVistiLog() {
        DateTime endTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        visitLogMapper.deleteVisitLog(endTime);
    }
}