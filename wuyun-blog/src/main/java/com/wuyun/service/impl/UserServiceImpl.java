package com.wuyun.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.User;
import com.wuyun.entity.UserRole;
import com.wuyun.enums.FilePathEnum;
import com.wuyun.exception.ServiceException;
import com.wuyun.mapper.MenuMapper;
import com.wuyun.mapper.RoleMapper;
import com.wuyun.mapper.UserMapper;
import com.wuyun.mapper.UserRoleMapper;
import com.wuyun.model.dto.*;
import com.wuyun.model.vo.*;
import com.wuyun.service.RedisService;
import com.wuyun.service.UserService;
import com.wuyun.strategy.context.UploadStrategyContext;
import com.wuyun.utils.PageUtils;
import com.wuyun.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.wuyun.constant.CommonConstant.*;
import static com.wuyun.constant.RedisConstant.*;
import static com.wuyun.utils.PageUtils.getLimit;
import static com.wuyun.utils.PageUtils.getSize;


/**
 * 用户业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@Service
//代替@Autowired，需要加上final修饰
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final MenuMapper menuMapper;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    private final RedisService redisService;

    private final UploadStrategyContext uploadStrategyContext;

    @Override
    public UserBackInfoVO getUserBackInfo() {
        //获取当前登录用户的ID
        Integer userId = StpUtil.getLoginIdAsInt();
        //查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getAvatar)
                .eq(User::getId, userId));
        //查询用户角色
        List<String> roleIdList = StpUtil.getRoleList();
        //获取用户权限列表
        List<String> permissionList1 = StpUtil.getPermissionList();
        List<String> permissionList = StpUtil.getPermissionList().stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return UserBackInfoVO.builder()
                .id(userId)
                .avatar(user.getAvatar())
                .roleList(roleIdList)
                .permissionList(permissionList)
                .build();
    }

    @Override
    public List<RouterVO> getUserMenu() {
        //查询用户菜单
        List<UserMenuVO> userMenuVOSList = menuMapper.selectMenuByUserId(StpUtil.getLoginIdAsInt());
        return recurRoutes(PARENT_ID ,userMenuVOSList);
    }

    @Override
    public PageResult<UserBackVO> getUserBackVOList(ConditionDTO condition) {
        //查询用户数量
        Long count = userMapper.selectCountUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询用户列表
        List<UserBackVO> UserBackVOList = userMapper.selectUserBackVOList(getLimit(), getSize(),condition);
        return new PageResult<>(UserBackVOList, count);
    }

    @Override
    public List<UserRoleVO> getUserRoleVOList() {
        return roleMapper.selectUserRoleList();
    }

    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) {
        //更新用户信息
        try {
            User newUser = User.builder().id(userRoleDTO.getId())
                                         .nickname(userRoleDTO.getNickname())
                                         .build();
            baseMapper.updateById(newUser);
            //删除用户角色
            userRoleMapper.delete(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, userRoleDTO.getId()));
            // 重新添加用户角色
            userRoleMapper.insertUserRole(userRoleDTO.getId(), userRoleDTO.getRoleIdList());
            // 删除Redis缓存中的角色
            SaSession sessionByLoginId = StpUtil.getSessionByLoginId(userRoleDTO.getId(), false);
            Optional.ofNullable(sessionByLoginId).ifPresent(saSession -> saSession.delete("Role_List"));
        } catch (Exception e) {
            throw new ServiceException("用户昵称已经存在");
        }
    }

    @Override
    public void updateUserStatus(DisableDTO disable) {
        // 更新用户状态
        User newUser = User.builder()
                .id(disable.getId())
                .isDisable(disable.getIsDisable())
                .build();
        userMapper.updateById(newUser);
        if (disable.getIsDisable().equals(TRUE)) {
            // 先踢下线
            StpUtil.logout(disable.getId());
            // 再封禁账号
            StpUtil.disable(disable.getId(), 86400);
        } else {
            // 解除封禁
            StpUtil.untieDisable(disable.getId());
        }
    }

    @Override
    public PageResult<OnlineVO> listOnlineUser(ConditionDTO condition) {
        // 查询所有会话token
        List<String> tokenList = StpUtil.searchTokenSessionId("", 0, -1, false);
        List<OnlineVO> onlineVOList = tokenList.stream()
                .map(token -> {
                    // 获取tokenSession
                    SaSession sessionBySessionId = StpUtil.getSessionBySessionId(token);
                    return (OnlineVO) sessionBySessionId.get(ONLINE_USER);
                })
                .filter(onlineVO -> StringUtils.isEmpty(condition.getKeyword()) || onlineVO.getNickname().contains(condition.getKeyword()))
                .sorted(Comparator.comparing(OnlineVO::getLoginTime).reversed())
                .collect(Collectors.toList());
        // 执行分页
        int fromIndex = getLimit().intValue();
        int size = getSize().intValue();
        int toIndex = onlineVOList.size() - fromIndex > size ? fromIndex + size : onlineVOList.size();
        List<OnlineVO> userOnlineList = onlineVOList.subList(fromIndex, toIndex);
        return new PageResult<>(userOnlineList, (long) onlineVOList.size());
    }

    @Override
    public void kickOutUser(String token) {
        StpUtil.logoutByTokenValue(token);
    }

    @Override
    public void updateAdminPassword(PasswordDTO password) {
        Integer userId = StpUtil.getLoginIdAsInt();
        // 查询旧密码是否正确
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, userId));
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(SecurityUtils.checkPw(user.getPassword(), password.getOldPassword()), "旧密码校验不通过!");
        // 正确则修改密码
        String newPassword = SecurityUtils.sha256Encrypt(password.getNewPassword());
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    @Override
    public UserInfoVO getUserInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getNickname, User::getAvatar, User::getUsername, User::getWebSite,
                        User::getIntro, User::getEmail, User::getLoginType)
                .eq(User::getId, userId));
        Set<Object> articleLikeSet = redisService.getSet(USER_ARTICLE_LIKE + userId);
        Set<Object> commentLikeSet = redisService.getSet(USER_COMMENT_LIKE + userId);
        Set<Object> talkLikeSet = redisService.getSet(USER_TALK_LIKE + userId);
        return UserInfoVO.builder()
                         .id(userId)
                         .avatar(user.getAvatar())
                         .nickname(user.getNickname())
                         .username(user.getUsername())
                         .email(user.getEmail())
                         .webSite(user.getWebSite())
                         .intro(user.getIntro())
                         .articleLikeSet(articleLikeSet)
                         .commentLikeSet(commentLikeSet)
                         .talkLikeSet(talkLikeSet)
                         .loginType(user.getLoginType())
                         .build();
    }

    @Override
    public void updatePassword(UserDTO user) {
        verifyCode(user.getUsername(), user.getCode());
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, user.getUsername()));
        Assert.notNull(existUser, "邮箱尚未注册！");
        // 根据用户名修改密码
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getPassword, SecurityUtils.sha256Encrypt(user.getPassword()))
                .eq(User::getUsername, user.getUsername()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserEmail(EmailDTO email) {
        verifyCode(email.getEmail(), email.getCode());
        User newUser = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .email(email.getEmail())
                .build();
        userMapper.updateById(newUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateUserAvatar(MultipartFile file) {
        // 头像上传
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        // 更新用户头像
        User newUser = User.builder()
                .id(StpUtil.getLoginIdAsInt())
                .avatar(avatar)
                .build();
        userMapper.updateById(newUser);
        return avatar;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfoDTO userInfo) {
        try {
            User newUser = User.builder()
                    .id(StpUtil.getLoginIdAsInt())
                    .nickname(userInfo.getNickname())
                    .intro(userInfo.getIntro())
                    .webSite(userInfo.getWebSite())
                    .build();
            userMapper.updateById(newUser);
        } catch (Exception e) {
            throw new ServiceException("用户昵称已存在");
        }
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code) {
        String sysCode = redisService.getObject(CODE_KEY + username);
        Assert.notBlank(sysCode, "验证码未发送或已过期！");
        Assert.isTrue(sysCode.equals(code), "验证码错误，请重新输入！");
    }

    /**
     * 递归生成路由列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 路由列表
     */
    private List<RouterVO> recurRoutes(Integer parentId, List<UserMenuVO> menuList) {
        List<RouterVO> list = new ArrayList<>();
        //非空判断
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> { //循环
                    RouterVO routeVO = new RouterVO();
                    routeVO.setName(menu.getMenuName());
                    routeVO.setPath(getRouterPath(menu));
                    routeVO.setComponent(getComponent(menu));
                    routeVO.setMeta(MetaVO.builder()
                            .title(menu.getMenuName())
                            .icon(menu.getIcon())
                            .hidden(menu.getIsHidden().equals(TRUE))
                            .build());
                    //判断当前菜单是否是目录
                    if (menu.getMenuType().equals(TYPE_DIR)) {
                        //如果是目录，则进行递归，创建子级菜单
                        List<RouterVO> children = recurRoutes(menu.getId(), menuList);
                        if (CollectionUtil.isNotEmpty(children)) {
                            routeVO.setAlwaysShow(true);
                            routeVO.setRedirect("noRedirect");
                        }
                        routeVO.setChildren(children);
                    //根据id判断当前菜单的父级菜单
                    } else if (isMenuFrame(menu)) {
                        routeVO.setMeta(null);
                        List<RouterVO> childrenList = new ArrayList<>();
                        RouterVO children = new RouterVO();
                        children.setName(menu.getMenuName());
                        children.setPath(menu.getPath());
                        children.setComponent(menu.getComponent());
                        children.setMeta(MetaVO.builder()
                                .title(menu.getMenuName())
                                .icon(menu.getIcon())
                                .hidden(menu.getIsHidden().equals(TRUE))
                                .build());
                        childrenList.add(children);
                        routeVO.setChildren(childrenList);
                    }
                    list.add(routeVO);
                }));
        return list;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(UserMenuVO menu) {
        String routerPath = menu.getPath();
        // 一级目录
        if (menu.getParentId().equals(PARENT_ID) && TYPE_DIR.equals(menu.getMenuType())) {
            routerPath = "/" + menu.getPath();
        }
        // 一级菜单
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(UserMenuVO menu) {
        String component = LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(UserMenuVO menu) {
        return menu.getParentId().equals(PARENT_ID) && TYPE_MENU.equals(menu.getMenuType());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(UserMenuVO menu) {
        return !menu.getParentId().equals(PARENT_ID) && TYPE_DIR.equals(menu.getMenuType());
    }
}
