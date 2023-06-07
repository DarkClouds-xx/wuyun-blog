package com.wuyun.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Role;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.RoleDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.RoleVO;

import java.util.List;


/**
 * 角色业务接口
 *
 * @author DarkClouds
 * @date 2023/05/11
 */
public interface RoleService extends IService<Role> {
    /**
     * 查询角色列表
     *
     * @param condition 条件
     * @return 角色列表
     */
    PageResult<RoleVO> listRoleVO(ConditionDTO condition);


    /**
     * 查看角色的菜单权限
     *
     * @param roleId 角色id
     * @return 菜单权限
     */
    List<Integer> listRoleMenuTree(String roleId);


    /**
     * 修改角色
     *
     * @param role 角色信息
     */
    void updateRole(RoleDTO role);

    /**
     * 添加角色
     *
     * @param roleDTO 角色dto
     */
    void addRole(RoleDTO roleDTO);


    /**
     * 删除角色
     *
     * @param roleIdList 角色id列表
     */
    void deleteRole(List<String> roleIdList);
}
