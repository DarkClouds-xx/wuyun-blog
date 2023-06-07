package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.Task;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.TaskBackVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务映射器
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Repository
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 查询定时任务数量
     *
     * @param condition 条件
     * @return 数量
     */
    Long countTaskBackVO(@Param("condition") ConditionDTO condition);

    /**
     * 查询定时任务列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 定时任务列表
     */
    List<TaskBackVO> selectTaskBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}
