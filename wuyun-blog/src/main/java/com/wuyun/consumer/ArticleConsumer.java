package com.wuyun.consumer;

import cn.hutool.json.JSONUtil;
import com.wuyun.model.dto.CanalDTO;
import com.wuyun.model.vo.ArticleSearchVO;
import com.wuyun.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static com.wuyun.constant.ElasticConstant.*;
import static com.wuyun.constant.MqConstant.*;


/**
 * 文章消费者
 *
 * @author DarkClouds
 * @date 2023/05/18
 */
@Component
@RequiredArgsConstructor
public class ArticleConsumer {

    private final ElasticsearchService elasticsearchService;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = ARTICLE_QUEUE, durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = ARTICLE_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = ARTICLE_KEY
            )})
    public void listenSaveArticle(Message message) {
        String data = new String(message.getBody(), StandardCharsets.UTF_8);
        CanalDTO canalDTO = JSONUtil.toBean(JSONUtil.toJsonStr(data), CanalDTO.class);
        if (canalDTO.getIsDdl()) {
            return;
        }
        ArticleSearchVO article = JSONUtil.toBean(JSONUtil.toJsonStr(canalDTO.getData().get(0)), ArticleSearchVO.class);
        switch (canalDTO.getType()) {
            case INSERT:
                elasticsearchService.addArticle(article);
            case UPDATE:
                elasticsearchService.updateArticle(article);
                break;
            case DELETE:
                elasticsearchService.deleteArticle(article.getId());
                break;
            default:
                break;
        }
    }
}