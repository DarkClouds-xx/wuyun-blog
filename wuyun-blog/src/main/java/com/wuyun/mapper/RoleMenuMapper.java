package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色映射器菜单
 *
 * @author DarkClouds
 * @date 2023/05/12
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据角色id删除角色菜单
     *
     * @param id 角色id
     */
    void deleteRoleMenuByRoleId(@Param("roleId") String id);


    /**
     * 添加角色菜单
     *
     * @param id         角色id
     * @param menuIdList 菜单id集合
     */
    void insertRoleMenu(@Param("roleId") String id, List<Integer> menuIdList);

    /**
     * 批量删除角色菜单
     *
     * @param roleIdList 角色id列表
     */
    void deleteRoleMenu(@Param("roleIdList") List<String> roleIdList);
}
