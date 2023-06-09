package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Message;
import com.wuyun.model.dto.CheckDTO;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.MessageDTO;
import com.wuyun.model.vo.MessageBackVO;
import com.wuyun.model.vo.MessageVO;
import com.wuyun.model.vo.PageResult;

import java.util.List;

/**
 * 留言业务接口
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
public interface MessageService extends IService<Message> {

    /**
     * 查看后台留言列表
     *
     * @param condition 条件
     * @return 后台留言列表
     */
    PageResult<MessageBackVO> listMessageBackVO(ConditionDTO condition);

    /**
     * 查看留言列表
     *
     * @return 留言列表
     */
    List<MessageVO> listMessageVO();

    /**
     * 添加留言
     *
     * @param message 留言
     */
    void addMessage(MessageDTO message);

    /**
     * 审核留言
     *
     * @param check 审核信息
     */
    void updateMessageCheck(CheckDTO check);
}
