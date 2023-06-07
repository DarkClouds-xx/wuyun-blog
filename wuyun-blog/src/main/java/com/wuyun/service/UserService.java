package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.User;
import com.wuyun.model.dto.*;
import com.wuyun.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
public interface UserService extends IService<User> {

    /**
     * 获取当前登录用户详情
     *
     * @return 后台用户信息
     */
    UserBackInfoVO getUserBackInfo();


    /**
     * 获取当前登录用户所拥有的菜单权限
     *
     * @return 登录用户菜单
     */
    List<RouterVO> getUserMenu();

    /**
     * 获取用户列表
     *
     * @param condition 条件
     * @return 后台登录用户信息
     */
    PageResult<UserBackVO> getUserBackVOList(ConditionDTO condition);

    /**
     * 查看用户角色选项
     *
     * @return 用户角色选项
     */
    List<UserRoleVO> getUserRoleVOList();

    /**
     * 更新用户角色
     *
     * @param userRoleDTO 用户角色dto
     */
    void updateUserRole(UserRoleDTO userRoleDTO);

    /**
     * 修改用户状态
     *
     * @param disable 禁用信息
     */
    void updateUserStatus(DisableDTO disable);

    /**
     * 查看在线用户列表
     *
     * @param condition 条件
     * @return 在线用户列表
     */
    PageResult<OnlineVO> listOnlineUser(ConditionDTO condition);

    /**
     * 下线用户
     *
     * @param token 在线token
     */
    void kickOutUser(String token);

    /**
     * 修改管理员密码
     *
     * @param password 密码
     */
    void updateAdminPassword(PasswordDTO password);

    /**
     * 获取登录用户信息
     *
     * @return 用户信息
     */
    UserInfoVO getUserInfo();

    /**
     * 修改用户邮箱
     *
     * @param email 邮箱信息
     */
    void updateUserEmail(EmailDTO email);

    /**
     * 修改用户头像
     *
     * @param file 头像
     * @return 头像链接
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     */
    void updateUserInfo(UserInfoDTO userInfo);

    /**
     * 修改用户密码
     *
     * @param user 用户密码
     */
    void updatePassword(UserDTO user);
}
