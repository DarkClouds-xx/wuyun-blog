package com.wuyun.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.wuyun.annotation.AccessLimit;
import com.wuyun.model.dto.GitDTO;
import com.wuyun.model.dto.LoginDTO;
import com.wuyun.model.dto.QqLoginDTO;
import com.wuyun.model.dto.RegisterDTO;
import com.wuyun.model.vo.Result;
import com.wuyun.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@Api(tags = "登录模块")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 用户登录
     *
     * @param loginDTO 登录参数
     * @return {@link String} Token
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDTO loginDTO) {
        return Result.success(loginService.login(loginDTO));
    }

    /**
     * 用户退出
     */
    @SaCheckLogin//登录校验 —— 只有登录之后才能进入该方法。
    @ApiOperation(value = "用户退出")
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }

    /**
     * 发送邮箱验证码
     *
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/code")
    public Result<?> sendCode(String username) {
        loginService.sendCode(username);
        return Result.success();
    }

    /**
     * 用户邮箱注册
     *
     * @param register 注册信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "用户邮箱注册")
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody RegisterDTO register) {
        loginService.register(register);
        return Result.success();
    }

    /**
     * Gitee登录
     *
     * @param data 第三方code
     * @return {@link Result<String>} Token
     */
    @ApiOperation(value = "Gitee登录")
    @PostMapping("/oauth/gitee")
    public Result<String> giteeLogin(@RequestBody GitDTO data) {
        return Result.success(loginService.giteeLogin(data));
    }

    /**
     * Github登录
     *
     * @param data 第三方code
     * @return {@link Result<String>} Token
     */
    @ApiOperation(value = "Github登录")
    @PostMapping("/oauth/github")
    public Result<String> githubLogin(@RequestBody GitDTO data) {
        return Result.success(loginService.githubLogin(data));
    }

    /**
     * QQ登录
     *
     * @param qqLogin QQ登录信息
     * @return {@link Result<String>} Token
     */
    @ApiOperation(value = "QQ登录")
    @PostMapping("/oauth/qq")
    public Result<String> qqLogin(@Validated @RequestBody QqLoginDTO qqLogin) {
        return Result.success(loginService.qqLogin(qqLogin));
    }

}
