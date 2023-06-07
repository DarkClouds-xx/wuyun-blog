package com.wuyun.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wuyun.annotation.OptLogger;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.MenuDTO;
import com.wuyun.model.vo.MenuOption;
import com.wuyun.model.vo.MenuTree;
import com.wuyun.model.vo.MenuVO;
import com.wuyun.model.vo.Result;
import com.wuyun.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyun.constant.OptTypeConstant.ADD;
import static com.wuyun.constant.OptTypeConstant.UPDATE;

/**
 * 菜单控制器
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Api(tags = "菜单模块")
@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 查看菜单列表
     *
     * @return {@link MenuVO} 菜单列表
     */
    @ApiOperation("查看菜单列表")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/list")
    public Result<List<MenuVO>> listMenuVO(ConditionDTO condition) {
        return Result.success(menuService.listMenuVO(condition));
    }

    /**
     * 获取菜单下拉树
     *
     * @return {@link MenuTree} 菜单树
     */
    @ApiOperation(value = "获取菜单下拉树")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/menu/getMenuTree")
    public Result<List<MenuTree>> getMenuTreeList() {
        return Result.success(menuService.getMenuTreeList());
    }

    /**
     * 获取菜单选项树
     * 不显示按钮
     * @return {@link MenuTree} 菜单树
     */
    @ApiOperation(value = "获取菜单选项树")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/getMenuOptions")
    public Result<List<MenuOption>> listMenuOption() {
        return Result.success(menuService.listMenuOption());
    }

    /**
     * 添加菜单
     *
     * @param menuDTO 菜单dto
     * @return {@link Result}<{@link ?}>
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加菜单")
    @SaCheckPermission("system:menu:add")
    @PostMapping("/admin/menu/add")
    public Result<?> addMenu(@Validated @RequestBody MenuDTO menuDTO) {
        menuService.addMenu(menuDTO);
        return Result.success();
    }

    /**
     * 编辑菜单
     *
     * @param menuId 菜单id
     * @return {@link Result}<{@link MenuDTO}>
     */
    @ApiOperation(value = "编辑菜单")
    @SaCheckPermission("system:menu:edit")
    @GetMapping("/admin/menu/edit/{menuId}")
    public Result<MenuDTO> editMenu(@PathVariable("menuId") Integer menuId){
        return Result.success(menuService.editMenu(menuId));
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation(value = "删除菜单")
    @SaCheckPermission("system:menu:delete")
    @DeleteMapping("/admin/menu/delete/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return Result.success();
    }

    /**
     * 更新菜单
     *
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改菜单")
    @SaCheckPermission("system:menu:update")
    @PutMapping("/admin/menu/update")
    public Result<?> updateMenu(@Validated @RequestBody MenuDTO menuDTO) {
        menuService.updateMenu(menuDTO);
        return Result.success();
    }

}
