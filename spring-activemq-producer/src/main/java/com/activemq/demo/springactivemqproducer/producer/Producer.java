package com.activemq.demo.springactivemqproducer.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Topic;
import java.util.List;

/**
 * @author: sunlei
 * @date: 2022/3/10
 * @description:
 */
@Component
public class Producer {
    @Resource
    private Topic topic;

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendTopic(ScriptMessageDto messageDto) {
        System.out.println("发送Topic消息内容 :"+ messageDto);
        jmsMessagingTemplate.convertAndSend(this.topic, JSONObject.toJSONString(messageDto));
    }
}
