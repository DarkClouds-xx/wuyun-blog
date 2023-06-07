package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色映射器
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {


    /**
     * 添加用户角色
     *
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    void insertUserRole(@Param("userId") Integer userId,@Param("roleIdList") List<String> roleIdList);
}
