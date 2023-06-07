package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 定时任务日志
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_task_log")
public class TaskLog {

    /**
     * 任务日志id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务组名
     */
    private String taskGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 日志信息
     */
    private String taskMessage;

    /**
     * 执行状态 (0失败 1正常)
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}