package com.wuyun.quartz.execution;

import com.wuyun.entity.Task;
import com.wuyun.quartz.utils.TaskInvokeUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;


/**
 * 定时任务处理（禁止并发执行）
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, Task task) throws Exception {
        TaskInvokeUtils.invokeMethod(task);
    }
}
