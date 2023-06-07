package com.wuyun.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wuyun.annotation.OptLogger;
import com.wuyun.annotation.VisitLogger;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.FriendDTO;
import com.wuyun.model.vo.FriendBackVO;
import com.wuyun.model.vo.FriendVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.Result;
import com.wuyun.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyun.constant.OptTypeConstant.*;

/**
 * 友链控制器
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Api(tags = "友链模块")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    /**
     * 查看友链后台列表
     *
     * @param condition 查询条件
     * @return {@link PageResult < FriendBackVO >} 后台友链列表
     */
    @ApiOperation(value = "查看友链后台列表")
    @SaCheckPermission("web:friend:list")
    @GetMapping("/admin/friend/list")
    public Result<PageResult<FriendBackVO>> listFriendBackVO(ConditionDTO condition) {
        return Result.success(friendService.listFriendBackVO(condition));
    }

    /**
     * 添加友链
     *
     * @param friend 友链
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加友链")
    @SaCheckPermission("web:friend:add")
    @PostMapping("/admin/friend/add")
    public Result<?> addFriend(@Validated @RequestBody FriendDTO friend) {
        friendService.addFriend(friend);
        return Result.success();
    }

    /**
     * 删除友链
     *
     * @param friendIdList 友链id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除友链")
    @SaCheckPermission("web:friend:delete")
    @DeleteMapping("/admin/friend/delete")
    public Result<?> deleteFriend(@RequestBody List<Integer> friendIdList) {
        friendService.removeByIds(friendIdList);
        return Result.success();
    }

    /**
     * 修改友链
     *
     * @param friend 友链
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改友链")
    @SaCheckPermission("web:friend:update")
    @PutMapping("/admin/friend/update")
    public Result<?> updateFriend(@Validated @RequestBody FriendDTO friend) {
        friendService.updateFriend(friend);
        return Result.success();
    }

    /**
     * 查看友链列表
     *
     * @return {@link Result< FriendVO >} 友链列表
     */
    @VisitLogger(value = "友链")
    @ApiOperation(value = "查看友链列表")
    @GetMapping("/friend/list")
    public Result<List<FriendVO>> listFriendVO() {
        return Result.success(friendService.listFriendVO());
    }
}
