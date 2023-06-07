package com.wuyun.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wuyun.annotation.OptLogger;
import com.wuyun.entity.Role;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.RoleDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.Result;
import com.wuyun.model.vo.RoleVO;
import com.wuyun.model.vo.UserMenuVO;
import com.wuyun.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyun.constant.OptTypeConstant.*;

/**
 * 角色控制器
 *
 * @author DarkClouds
 * @date 2023/05/11
 */
@Api(tags = "角色模块")
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 查看角色列表
     *
     * @param condition 查询条件
     * @return {@link RoleVO} 角色列表
     */
    @ApiOperation(value = "查看角色列表")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/role/list")
    public Result<PageResult<RoleVO>> listRoleVO(ConditionDTO condition) {
        return Result.success(roleService.listRoleVO(condition));
    }

    /**
     * 查看角色的菜单权限
     *
     * @param roleId 角色id
     * @return {@link List <Integer>} 角色的菜单权限
     */
    @ApiOperation(value = "查看角色的菜单权限")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/role/menu/{roleId}")
    public Result<List<Integer>> listRoleMenuTree(@PathVariable("roleId") String roleId) {
        return Result.success(roleService.listRoleMenuTree(roleId));
    }

    /**
     * 修改角色
     *
     * @param role 角色信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改角色")
    @SaCheckPermission("system:role:update")
    @PutMapping("/admin/role/update")
    public Result<?> updateRole(@Validated @RequestBody RoleDTO role) {
        roleService.updateRole(role);
        return Result.success();
    }

    /**
     * 添加角色
     *
     * @param roleDTO 角色
     * @return {@link Result}<{@link ?}>
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加角色")
    @SaCheckPermission("system:role:add")
    @PostMapping("/admin/role/add")
    public Result<?> addRole(@Validated @RequestBody RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param roleIdList 角色id列表
     * @return {@link Result}<{@link ?}>
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除角色")
    @SaCheckPermission("system:role:delete")
    @DeleteMapping("/admin/role/delete")
    public Result<?> deleteRole(@RequestBody List<String> roleIdList){
        roleService.deleteRole(roleIdList);
        return Result.success();
    }
}
