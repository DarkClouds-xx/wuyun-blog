package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.TaskLog;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.TaskLogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务日志映射器
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Repository
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    /**
     * 查询定时任务日志数量
     *
     * @param condition 条件
     * @return 定时任务日志数量
     */
    Long selectTaskLogCount(@Param("condition") ConditionDTO condition);

    /**
     * 查询定时任务日志列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 定时任务日志列表
     */
    List<TaskLogVO> selectTaskLogVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}
