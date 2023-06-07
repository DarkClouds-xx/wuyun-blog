package com.wuyun.service;

import com.wuyun.model.dto.GitDTO;
import com.wuyun.model.dto.LoginDTO;
import com.wuyun.model.dto.QqLoginDTO;
import com.wuyun.model.dto.RegisterDTO;

/**
 * 登录业务接口
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
public interface LoginService {
    /**
     * 用户登录
     *
     * @param login 登录参数
     * @return token
     */
    String login(LoginDTO login);

    /**
     * 发送验证码
     *
     * @param username 用户名
     */
    void sendCode(String username);

    /**
     * 用户注册
     *
     * @param register 注册信息
     */
    void register(RegisterDTO register);

    /**
     * Gitee登录
     *
     * @param data 第三方code
     * @return Token
     */
    String giteeLogin(GitDTO data);

    /**
     * Github登录
     *
     * @param data 第三方code
     * @return Token
     */
    String githubLogin(GitDTO data);

    /**
     * QQ登录
     *
     * @param qqLogin QQ登录信息
     * @return token
     */
    String qqLogin(QqLoginDTO qqLogin);
}
