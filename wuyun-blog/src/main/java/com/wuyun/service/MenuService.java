package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Menu;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.MenuDTO;
import com.wuyun.model.vo.MenuOption;
import com.wuyun.model.vo.MenuTree;
import com.wuyun.model.vo.MenuVO;
import com.wuyun.model.vo.UserMenuVO;

import java.util.List;

/**
 * 菜单业务接口
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查看菜单列表
     *
     * @param condition 查询条件
     * @return 菜单列表
     */
    List<MenuVO> listMenuVO(ConditionDTO condition);


    /**
     * 获取菜单下拉树
     *
     * @return 菜单下拉树
     */
    List<MenuTree> getMenuTreeList();

    /**
     * 获取菜单选项树
     *
     * @return 菜单选项树
     */
    List<MenuOption> listMenuOption();


    /**
     * 添加菜单
     *
     * @param menuDTO 菜单dto
     */
    void addMenu(MenuDTO menuDTO);

    /**
     * 编辑菜单
     *
     * @param menuId 菜单id
     * @return {@link MenuVO}
     */
    MenuDTO editMenu(Integer menuId);

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     */
    void deleteMenu(Integer menuId);

    /**
     * 修改菜单
     *
     * @param menuDTO 菜单dto
     */
    void updateMenu(MenuDTO menuDTO);

}
