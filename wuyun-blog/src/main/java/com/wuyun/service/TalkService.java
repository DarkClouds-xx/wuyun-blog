package com.wuyun.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Talk;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.TalkDTO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.TalkBackInfoVO;
import com.wuyun.model.vo.TalkBackVO;
import com.wuyun.model.vo.TalkVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 说说业务接口
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
public interface TalkService extends IService<Talk> {

    /**
     * 查看后台说说列表
     *
     * @param condition 条件
     * @return {@link PageResult< TalkBackVO >} 说说列表
     */
    PageResult<TalkBackVO> listTalkBackVO(ConditionDTO condition);

    /**
     * 上传说说图片
     *
     * @param file 文件
     * @return 说说图片地址
     */
    String uploadTalkCover(MultipartFile file);

    /**
     * 添加说说
     *
     * @param talk 说说
     */
    void addTalk(TalkDTO talk);

    /**
     * 删除说说
     *
     * @param talkId 说说id
     */
    void deleteTalk(Integer talkId);

    /**
     * 编辑说说
     *
     * @param talkId 说说id
     * @return 说说
     */
    TalkBackInfoVO editTalk(Integer talkId);

    /**
     * 修改说说
     *
     * @param talk 说说
     */
    void updateTalk(TalkDTO talk);

    /**
     * 查看首页说说
     *
     * @return 首页说说
     */
    List<String> listTalkHome();

    /**
     * 查看说说列表
     *
     * @return 说说列表
     */
    PageResult<TalkVO> listTalkVO();

    /**
     * 查看说说
     *
     * @param talkId 说说id
     * @return 说说
     */
    TalkVO getTalkById(Integer talkId);
}
