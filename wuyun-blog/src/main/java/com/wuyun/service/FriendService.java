package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Friend;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.FriendDTO;
import com.wuyun.model.vo.FriendBackVO;
import com.wuyun.model.vo.FriendVO;
import com.wuyun.model.vo.PageResult;

import java.util.List;


/**
 * 友链业务接口
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
public interface FriendService extends IService<Friend> {

    /**
     * 查看后台友链列表
     *
     * @param condition 查询条件
     * @return 后台友链列表
     */
    PageResult<FriendBackVO> listFriendBackVO(ConditionDTO condition);

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendVO> listFriendVO();

    /**
     * 添加友链
     *
     * @param friend 友链
     */
    void addFriend(FriendDTO friend);

    /**
     * 修改友链
     *
     * @param friend 友链
     */
    void updateFriend(FriendDTO friend);

}
