package com.wuyun.consumer;

import com.wuyun.model.dto.MailDTO;
import com.wuyun.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.wuyun.constant.MqConstant.*;


/**
 * 邮件消费者
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = EMAIL_SIMPLE_QUEUE, durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = EMAIL_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = EMAIL_SIMPLE_KEY
            )})
    public void listenSendSimpleEmail(@Payload MailDTO mail) {
        emailService.sendSimpleMail(mail);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = EMAIL_HTML_QUEUE, durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = EMAIL_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = EMAIL_HTML_KEY
            )})
    public void listenSendHtmlEmail(@Payload MailDTO mail) {
        emailService.sendHtmlMail(mail);
    }
}