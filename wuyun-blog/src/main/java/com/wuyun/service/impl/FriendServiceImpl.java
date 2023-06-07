package com.wuyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.Friend;
import com.wuyun.mapper.FriendMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.FriendDTO;
import com.wuyun.model.vo.FriendBackVO;
import com.wuyun.model.vo.FriendVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.service.FriendService;
import com.wuyun.utils.BeanCopyUtils;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Service
@RequiredArgsConstructor
public class FriendServiceImpl extends ServiceImpl<FriendMapper , Friend> implements FriendService {

    private final FriendMapper friendMapper;

    @Override
    public PageResult<FriendBackVO> listFriendBackVO(ConditionDTO condition) {
        // 查询友链数量
        Long count = friendMapper.selectCount(new LambdaQueryWrapper<Friend>()
                .like(StringUtils.hasText(condition.getKeyword()), Friend::getName, condition.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台友链列表
        List<FriendBackVO> friendBackVOList = friendMapper.selectFriendBackVOList(PageUtils.getLimit(), PageUtils.getSize(), condition.getKeyword());
        return new PageResult<>(friendBackVOList, count);
    }

    @Override
    public void addFriend(FriendDTO friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 添加友链
        baseMapper.insert(newFriend);
    }

    @Override
    public void updateFriend(FriendDTO friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 更新友链
        baseMapper.updateById(newFriend);
    }

    @Override
    public List<FriendVO> listFriendVO() {
        // 查询友链列表
        return friendMapper.selectFriendVOList();
    }

}
