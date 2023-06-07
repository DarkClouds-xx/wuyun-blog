package com.wuyun.quartz.execution;

import com.wuyun.entity.Task;
import com.wuyun.quartz.utils.TaskInvokeUtils;
import org.quartz.JobExecutionContext;


/**
 * 定时任务处理（允许并发执行）
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, Task task) throws Exception {
        TaskInvokeUtils.invokeMethod(task);
    }
}
