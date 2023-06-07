package com.wuyun.quartz.execution;

import com.wuyun.constant.ScheduleConstant;
import com.wuyun.entity.Task;
import com.wuyun.entity.TaskLog;
import com.wuyun.mapper.TaskLogMapper;
import com.wuyun.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static com.wuyun.constant.CommonConstant.FALSE;
import static com.wuyun.constant.CommonConstant.TRUE;


/**
 * 抽象quartz调用
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@SuppressWarnings(value = "all")
public abstract class AbstractQuartzJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        Task task = new Task();
        //将 JobExecutionContext 中存储的任务属性拷贝到 Task 对象中
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstant.TASK_PROPERTIES), task);
        try {
            before(context, task);
            doExecute(context, task);
            after(context, task, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, task, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param task    系统计划任务
     */
    protected void before(JobExecutionContext context, Task task) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param task    系统计划任务
     */
    protected void after(JobExecutionContext context, Task task, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();
        final TaskLog taskLog = new TaskLog();
        taskLog.setTaskName(task.getTaskName());
        taskLog.setTaskGroup(task.getTaskGroup());
        taskLog.setInvokeTarget(task.getInvokeTarget());
        long runTime = new Date().getTime() - startTime.getTime();
        taskLog.setTaskMessage(taskLog.getTaskName() + " 总共耗时：" + runTime + "毫秒");
        if (e != null) {
            taskLog.setStatus(FALSE);
            String errorMsg = StringUtils.substring(e.getMessage(), 0, 2000);
            taskLog.setErrorInfo(errorMsg);
        } else {
            taskLog.setStatus(TRUE);
        }
        // 写入数据库当中
        SpringUtils.getBean(TaskLogMapper.class).insert(taskLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param task    系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, Task task) throws Exception;
}
