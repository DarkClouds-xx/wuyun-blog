package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.User;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.UserBackInfoVO;
import com.wuyun.model.vo.UserBackVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户映射器
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询后台用户数量
     *
     * @param condition 条件
     * @return 用户数量
     */
    Long selectCountUser(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台用户列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 查询条件
     * @return 后台用户列表
     */
    List<UserBackVO> selectUserBackVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition );
}
