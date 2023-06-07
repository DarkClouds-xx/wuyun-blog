package com.wuyun.service;

import com.wuyun.model.dto.MailDTO;

/**
 * 邮件服务接口
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
public interface EmailService {

    /**
     * 发送简单邮件
     *
     * @param mailDTO 邮件信息
     */
    void sendSimpleMail(MailDTO mailDTO);

    /**
     * 发送HTML邮件
     *
     * @param mailDTO 邮件信息
     */
    void sendHtmlMail(MailDTO mailDTO);

}
