package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.Menu;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.MenuDTO;
import com.wuyun.model.vo.MenuOption;
import com.wuyun.model.vo.MenuTree;
import com.wuyun.model.vo.MenuVO;
import com.wuyun.model.vo.UserMenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单映射器
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询用户菜单列表
     *
     * @param userId 用户id
     * @return 用户菜单列表
     */
    List<UserMenuVO> selectMenuByUserId(@Param("userId") Integer userId);

    /**
     * 根据角色id查询对应权限
     *
     * @param roleId id
     * @return 权限标识
     */
    List<String> selectPermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 查询菜单列表
     *
     * @param condition 查询条件
     * @return 菜单列表
     */
    List<MenuVO> selectMenuVOList(@Param("condition") ConditionDTO condition);

    /**
     * 查询菜单下拉树
     *
     * @return 菜单下拉树
     */
    List<MenuTree> selectMenuTree();

    /**
     * 查询菜单选项树
     *
     * @return 菜单选项树
     */
    List<MenuOption> selectMenuOptions();

    /**
     * 根据ID查询菜单信息
     *
     * @param menuId 菜单id
     * @return {@link MenuDTO}
     */
    MenuDTO selectMenuById(@Param("menuId") Integer menuId);
}
