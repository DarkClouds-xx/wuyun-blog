package com.wuyun.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.Menu;
import com.wuyun.entity.RoleMenu;
import com.wuyun.mapper.MenuMapper;
import com.wuyun.mapper.RoleMenuMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.MenuDTO;
import com.wuyun.model.vo.MenuOption;
import com.wuyun.model.vo.MenuTree;
import com.wuyun.model.vo.MenuVO;
import com.wuyun.service.MenuService;
import com.wuyun.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wuyun.constant.CommonConstant.PARENT_ID;


/**
 * 菜单业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Service
//代替@Autowired，需要加上final修饰
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;

    private final RoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuVO> listMenuVO(ConditionDTO condition) {
        //查询当前菜单列表
        List<MenuVO> menuVOList = menuMapper.selectMenuVOList(condition);
        // 获取当前菜单列表的id
        Set<Integer> menuIdList = menuVOList.stream()
                .map(MenuVO::getId)//数据转换
                .collect(Collectors.toSet());
        return menuVOList.stream().map(menuVO -> {
            //获取当前菜单的父级id
            Integer parentId = menuVO.getParentId();
            // parentId不在当前菜单id列表，说明为父级菜单id，根据此id作为递归的开始条件节点
            if (!menuIdList.contains(parentId)) {
                menuIdList.add(parentId);
                return recurMenuList(parentId, menuVOList);
            }
            return new ArrayList<MenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    @Override
    public List<MenuTree> getMenuTreeList() {
        List<MenuTree> menuTreeList = menuMapper.selectMenuTree();
        return recurMenuTreeList(PARENT_ID, menuTreeList);
    }

    @Override
    public List<MenuOption> listMenuOption() {
        List<MenuOption> menuOptionList = menuMapper.selectMenuOptions();
        return recurMenuOptionList(PARENT_ID, menuOptionList);
    }

    @Override
    public void addMenu(MenuDTO menuDTO) {
        //判断要添加的菜单是否已经存在
        Menu existMenu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getMenuName, menuDTO.getMenuName()));
        //不为空，则抛出异常信息
        Assert.isNull(existMenu, menuDTO.getMenuName() + "菜单已存在");
        //将menuDTO属性拷贝到Menu实体类
        Menu newMenu = BeanCopyUtils.copyBean(menuDTO, Menu.class);
        //添加数据
        baseMapper.insert(newMenu);
    }

    @Override
    public MenuDTO editMenu(Integer menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        // 菜单下是否存在子菜单
        Long menuCount = menuMapper.selectCount(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getParentId, menuId));
        Assert.isFalse(menuCount > 0, "存在子菜单");
        // 菜单是否已分配给角色
        Long roleCount = roleMenuMapper.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        Assert.isFalse(roleCount > 0, "菜单权限已分配，请先取消相关权限");
        // 删除菜单
        menuMapper.deleteById(menuId);
    }

    @Override
    public void updateMenu(MenuDTO menuDTO) {
        // 判断名称是否存在
        Menu existMenu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getMenuName, menuDTO.getMenuName()));
        /**
         * Assert.isFalse() 方法是一个断言方法，用于判断一个条件是否为 false，
         * 如果条件为 true，就会抛出 AssertionError 异常。
         * 在这个方法中，我们使用了 Objects.nonNull() 方法来判断 existMenu 是否为空，
         * 如果不为空，则说明该菜单名称已经存在。
         * 同时，还要判断 existMenu 的 ID 是否与待更新的菜单 ID 相同，
         * 如果不同，则说明该菜单名称已经被其他菜单占用，也需要抛出异常。
         */
        Assert.isFalse(Objects.nonNull(existMenu) && !existMenu.getId().equals(menuDTO.getId()),
                menuDTO.getMenuName() + "菜单已存在");
        Menu newMenu = BeanCopyUtils.copyBean(menuDTO, Menu.class);
        baseMapper.updateById(newMenu);
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父菜单id
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private List<MenuVO> recurMenuList(Integer parentId, List<MenuVO> menuList) {
        return menuList.stream()
                //过滤id，过滤后的菜单都是父菜单的子菜单
                .filter(menuVO -> menuVO.getParentId().equals(parentId))
                // 使用 peek() 方法对每个过滤后的菜单应用一个函数。
                // 该函数将菜单的子菜单设置为递归调用 recurMenuList() 方法的结果，该方法使用菜单自己的 ID 和原始的 menuList 参数。
                // 这意味着每个菜单的子菜单本身也是一个菜单列表，可能也有子菜单
                .peek(menuVO -> menuVO.setChildren(recurMenuList(menuVO.getId(), menuList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单下拉树
     *
     * @param parentId     父菜单id
     * @param menuTreeList 菜单树列表
     * @return 菜单列表
     */
    private List<MenuTree> recurMenuTreeList(Integer parentId, List<MenuTree> menuTreeList) {
        return menuTreeList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(recurMenuTreeList(menu.getId(), menuTreeList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单选项树
     *
     * @param parentId       父菜单id
     * @param menuOptionList 菜单树列表
     * @return 菜单列表
     */
    private List<MenuOption> recurMenuOptionList(Integer parentId, List<MenuOption> menuOptionList) {
        return menuOptionList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(recurMenuOptionList(menu.getValue(), menuOptionList)))
                .collect(Collectors.toList());
    }

}
