package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.Role;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.RoleVO;
import com.wuyun.model.vo.UserRoleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色映射器
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户角色
     *
     * @param userId 用户id
     * @return 角色
     */
    List<String> selectRoleListByUserId(@Param("userId") Object userId);

    /**
     * 查询后台角色数量
     *
     * @param condition 查询条件
     * @return 后台角色数量
     */
    Long selectCountRoleVO(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台角色列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 查询条件
     * @return 后台角色列表
     */
    List<RoleVO> selectRoleVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    /**
     * 查询用户角色选项
     *
     * @return 用户角色选项
     */
    List<UserRoleVO> selectUserRoleList();


    /**
     * 查看角色的菜单权限
     *
     * @param roleId 角色id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> listRoleMenuTree(@Param("roleId") String roleId);

}
