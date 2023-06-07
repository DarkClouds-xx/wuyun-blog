package com.wuyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.Message;
import com.wuyun.entity.SiteConfig;
import com.wuyun.mapper.MessageMapper;
import com.wuyun.mapper.SiteConfigMapper;
import com.wuyun.model.dto.CheckDTO;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.MessageDTO;
import com.wuyun.model.vo.MessageBackVO;
import com.wuyun.model.vo.MessageVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.service.MessageService;
import com.wuyun.service.SiteConfigService;
import com.wuyun.utils.BeanCopyUtils;
import com.wuyun.utils.HTMLUtils;
import com.wuyun.utils.IpUtils;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wuyun.constant.CommonConstant.FALSE;
import static com.wuyun.constant.CommonConstant.TRUE;

/**
 * 留言业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    private final MessageMapper messageMapper;

    private final SiteConfigService siteConfigService;

    private final HttpServletRequest request;

    @Override
    public PageResult<MessageBackVO> listMessageBackVO(ConditionDTO condition) {
        // 查询留言数量
        Long count = messageMapper.selectCount(new LambdaQueryWrapper<Message>()
                .like(StringUtils.hasText(condition.getKeyword()), Message::getNickname, condition.getKeyword())
                .eq(Objects.nonNull(condition.getIsCheck()), Message::getIsCheck, condition.getIsCheck()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台留言列表
        List<MessageBackVO> messageBackVOList = messageMapper.selectMessageBackVOList(PageUtils.getLimit(), PageUtils.getSize(), condition);
        return new PageResult<>(messageBackVOList, count);
    }

    @Override
    public List<MessageVO> listMessageVO() {
        // 查询留言列表
        return messageMapper.selectMessageVOList();
    }

    @Override
    public void addMessage(MessageDTO message) {
        //获取网站配置
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        //获取留言审核是否开启
        Integer messageCheck = siteConfig.getMessageCheck();
        //获取留言用户的ip和位置
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message newMessage = BeanCopyUtils.copyBean(message, Message.class);
        newMessage.setMessageContent(HTMLUtils.filter(message.getMessageContent()));
        newMessage.setIpAddress(ipAddress);
        newMessage.setIsCheck(messageCheck.equals(FALSE) ? TRUE : FALSE);
        newMessage.setIpSource(ipSource);
        //添加留言
        messageMapper.insert(newMessage);
    }

    @Override
    public void updateMessageCheck(CheckDTO check) {
        // 修改留言审核状态
        List<Message> messageList = check.getIdList()
                .stream()
                .map(id -> Message.builder()
                        .id(id)
                        .isCheck(check.getIsCheck())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }
}
