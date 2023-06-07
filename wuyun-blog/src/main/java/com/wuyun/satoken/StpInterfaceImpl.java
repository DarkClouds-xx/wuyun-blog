package com.wuyun.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.wuyun.mapper.MenuMapper;
import com.wuyun.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.wuyun.constant.CommonConstant.ADMIN;


/**
 * 自定义权限验证接口扩展
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Component // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final MenuMapper menuMapper;

    private final RoleMapper roleMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     *
     * @param loginId   登录用户id
     * @param loginType 登录账号类型
     * @return 权限集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 声明权限码集合
        List<String> permissionList = new ArrayList<>();
        // 遍历角色列表，查询拥有的权限码
        for (String roleId : getRoleList(loginId, loginType)) {
            //如果该用户角色为管理员，则拥有所有权限
            if (ADMIN.equals(roleId)) {
                permissionList.add("*");
                return permissionList;
            }
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleId);
            List<String> list = roleSession.get("Permission_List", () -> menuMapper.selectPermissionByRoleId(roleId));
            permissionList.addAll(list);
        }
        // 返回权限码集合
        return permissionList;
    }

    /**
     * 返回一个账号所拥有的可用角色标识集合
     *
     * @param loginId   登录用户id
     * @param loginType 登录账号类型
     * @return 角色集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        // 从数据库查询这个账号id拥有的角色列表
        return session.get("Role_List", () -> roleMapper.selectRoleListByUserId(loginId));
    }

}