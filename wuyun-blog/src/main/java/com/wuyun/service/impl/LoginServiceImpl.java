package com.wuyun.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wuyun.entity.SiteConfig;
import com.wuyun.entity.User;
import com.wuyun.entity.UserRole;
import com.wuyun.enums.LoginTypeEnum;
import com.wuyun.mapper.RoleMapper;
import com.wuyun.mapper.UserMapper;
import com.wuyun.mapper.UserRoleMapper;
import com.wuyun.model.dto.*;
import com.wuyun.service.LoginService;
import com.wuyun.service.RedisService;
import com.wuyun.strategy.context.SocialLoginStrategyContext;
import com.wuyun.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

import static com.wuyun.constant.CommonConstant.*;
import static com.wuyun.constant.MqConstant.EMAIL_EXCHANGE;
import static com.wuyun.constant.MqConstant.EMAIL_SIMPLE_KEY;
import static com.wuyun.constant.RedisConstant.*;
import static com.wuyun.utils.CommonUtils.checkEmail;
import static com.wuyun.enums.LoginTypeEnum.*;
import static com.wuyun.enums.RoleEnum.*;

/**
 *  登录业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@Service
//代替@Autowired，需要加上final修饰
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    private final RabbitTemplate rabbitTemplate;

    private final RedisService redisService;

    private final UserRoleMapper userRoleMapper;

    private final SocialLoginStrategyContext socialLoginStrategyContext;

    @Override
    public String login(LoginDTO login) {

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId)
                .eq(User::getUsername, login.getUsername())
                .eq(User::getPassword, SecurityUtils.sha256Encrypt(login.getPassword())));
        // 进行断言，如果查询结果为空，则抛出异常
        Assert.notNull(user,"用户不存在或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        //返回ToKen
        return StpUtil.getTokenValue();
    }

    @Override
    public void sendCode(String username) {
        cn.hutool.core.lang.Assert.isTrue(checkEmail(username), "请输入正确的邮箱！");
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 6);
        String code = randomGenerator.generate();
        MailDTO mailDTO = MailDTO.builder()
                .toEmail(username)
                .subject(CAPTCHA)
                .content("您的验证码为 " + code + " 有效期为" + CODE_EXPIRE_TIME + "分钟")
                .build();
        // 验证码存入消息队列
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_SIMPLE_KEY, mailDTO);
        // 验证码存入redis
        redisService.setObject(CODE_KEY + username, code, CODE_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO register) {
        //校验验证码
        verifyCode(register.getUsername(), register.getCode());
        //根据用户邮箱判断用户是否已经存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, register.getUsername()));
        cn.hutool.core.lang.Assert.isNull(user, "邮箱已注册！");
        //获取网站配置，给用户设置默认的头像
        SiteConfig siteConfig = redisService.getObject(SITE_SETTING);
        // 添加用户
        User newUser = User.builder()
                .username(register.getUsername())
                .email(register.getUsername())
                .nickname(USER_NICKNAME + IdWorker.getId())
                .avatar(siteConfig.getUserAvatar())
                .password(SecurityUtils.sha256Encrypt(register.getPassword()))
                .loginType(EMAIL.getLoginType())
                .isDisable(FALSE)
                .build();
        userMapper.insert(newUser);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(newUser.getId())
                .roleId(USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String giteeLogin(GitDTO data) {
        return socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(data), LoginTypeEnum.GITEE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String githubLogin(GitDTO data) {
        return socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(data), LoginTypeEnum.GITHUB);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String qqLogin(QqLoginDTO qqLogin) {
        return socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(qqLogin), LoginTypeEnum.QQ);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code) {
        String sysCode = redisService.getObject(CODE_KEY + username);
        cn.hutool.core.lang.Assert.notBlank(sysCode, "验证码未发送或已过期！");
        cn.hutool.core.lang.Assert.isTrue(sysCode.equals(code), "验证码错误，请重新输入！");
    }
}
