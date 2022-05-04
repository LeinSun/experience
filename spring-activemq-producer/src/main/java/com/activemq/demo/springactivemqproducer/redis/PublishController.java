package com.activemq.demo.springactivemqproducer.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @author: sunlei
 * @date: 2022/4/28
 * @description:
 */
@RestController
public class PublishController {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 向通道中发布消息
     * @param channel 通道名
     * @param message 消息内容
     * @return
     */
    @PostMapping("/publish/{channel}")
    public String publish(@PathVariable String channel, @RequestBody MassageDTO message) {
        //向通道中发送通知
        redisTemplate.convertAndSend(channel, message);
        return "success";
    }

}
