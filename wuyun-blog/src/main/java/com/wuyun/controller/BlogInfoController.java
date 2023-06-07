package com.wuyun.controller;

import com.wuyun.model.vo.BlogBackInfoVO;
import com.wuyun.model.vo.BlogInfoVO;
import com.wuyun.model.vo.Result;
import com.wuyun.service.BlogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客控制器
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
@Api(tags = "博客模块")
@RestController
@RequiredArgsConstructor
public class BlogInfoController {

    private final BlogInfoService blogInfoService;

    /**
     * 上传访客信息
     *
     * @return {@link Result<>}
     */
    @ApiOperation(value = "上传访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.success();
    }

    /**
     * 查看后台信息
     *
     * @return {@link Result< BlogBackInfoVO >} 后台信息
     */
    @ApiOperation(value = "查看后台信息")
    @GetMapping("/admin")
    public Result<BlogBackInfoVO> getBlogBackInfo() {
        return Result.success(blogInfoService.getBlogBackInfo());
    }

    /**
     * 查看博客信息
     *
     * @return {@link Result< BlogInfoVO >} 博客信息
     */
    @ApiOperation(value = "查看博客信息")
    @GetMapping("/")
    public Result<BlogInfoVO> getBlogInfo() {
        return Result.success(blogInfoService.getBlogInfo());
    }
}
