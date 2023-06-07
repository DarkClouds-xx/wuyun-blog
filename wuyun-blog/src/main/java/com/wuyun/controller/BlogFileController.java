package com.wuyun.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wuyun.annotation.OptLogger;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.FolderDTO;
import com.wuyun.model.vo.FileVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.Result;
import com.wuyun.service.BlogFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.wuyun.constant.OptTypeConstant.*;

/**
 * 博客文件控制器
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Api(tags = "文件模块")
@RestController
@RequiredArgsConstructor
public class BlogFileController {

    private final BlogFileService blogFileService;

    /**
     * 查看文件列表
     *
     * @param condition 查询条件
     * @return {@link Result< FileVO >} 文件列表
     */
    @ApiOperation(value = "查看文件列表")
    @SaCheckPermission("system:file:list")
    @GetMapping("/admin/file/list")
    public Result<PageResult<FileVO>> listFileVOList(ConditionDTO condition) {
        return Result.success(blogFileService.listFileVOList(condition));
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link Result<>}
     */
    @OptLogger(value = UPLOAD)
    @ApiOperation(value = "上传文件")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @SaCheckPermission("system:file:upload")
    @PostMapping("/admin/file/upload")
    public Result<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        blogFileService.uploadFile(file, path);
        return Result.success();
    }

    /**
     * 创建目录
     *
     * @param folder 目录信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "创建目录")
    @SaCheckPermission("system:file:createFolder")
    @PostMapping("/admin/file/createFolder")
    public Result<?> createFolder(@Validated @RequestBody FolderDTO folder) {
        blogFileService.createFolder(folder);
        return Result.success();
    }

    /**
     * 删除文件
     *
     * @param fileIdList 文件id列表
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除文件")
    @SaCheckPermission("system:file:delete")
    @DeleteMapping("/admin/file/delete")
    public Result<?> deleteFile(@RequestBody List<Integer> fileIdList) {
        blogFileService.deleteFile(fileIdList);
        return Result.success();
    }

    /**
     * 下载文件
     *
     * @param fileId 文件id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "下载文件")
    @GetMapping("/file/download/{fileId}")
    public Result<?> downloadFile(@PathVariable("fileId") Integer fileId) {
        blogFileService.downloadFile(fileId);
        return Result.success();
    }
}
