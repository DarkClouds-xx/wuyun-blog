package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.Talk;
import com.wuyun.model.vo.TalkBackInfoVO;
import com.wuyun.model.vo.TalkBackVO;
import com.wuyun.model.vo.TalkVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说说映射器
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
@Repository
public interface TalkMapper extends BaseMapper<Talk> {

    /**
     * 查询后台说说列表
     *
     * @param limit  页码
     * @param size   大小
     * @param status 状态
     * @return 后台说说列表
     */
    List<TalkBackVO> selectTalkBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("status") Integer status);

    /**
     * 根据id查询后台说说
     *
     * @param talkId 说说id
     * @return 后台说说
     */
    TalkBackInfoVO selectTalkBackById(Integer talkId);

    /**
     * 查询说说列表
     *
     * @param limit 页码
     * @param size  大小
     * @return 说说列表
     */
    List<TalkVO> selectTalkList(@Param("limit") Long limit, @Param("size") Long size);

    /**
     * 根据id查询说说
     *
     * @param talkId 说说id
     * @return 说说
     */
    TalkVO selectTalkById(Integer talkId);
}
