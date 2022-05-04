package com.activemq.demo.springactivemqconsumer.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author: sunlei
 * @date: 2022/4/27
 * @description:
 */
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
//        redisTemplate.setValueSerializer(new MyRedisSerializer<>(MassageDTO.class));
        RedisSerializer<MassageDTO> serializer = new MyRedisSerializer<>(MassageDTO.class);
        MassageDTO msgBody = serializer.deserialize(body);
        System.out.println(MessageFormat.format("收到消息: <{0}>", msgBody));
        byte[] channel = message.getChannel();
        String msgChannel = (String) stringRedisTemplate.getKeySerializer().deserialize(channel);
        System.out.println(MessageFormat.format("消息所在的通道: <{0}>", msgChannel));
        String msgPattern = new String(pattern);
        System.out.println(MessageFormat.format("匹配规则: <{0}>", msgPattern));
    }

}
