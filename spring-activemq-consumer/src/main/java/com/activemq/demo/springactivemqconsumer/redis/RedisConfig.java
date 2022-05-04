package com.activemq.demo.springactivemqconsumer.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author: sunlei
 * @date: 2022/4/28
 * @description:
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisMessageListener redisMessageListener;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("demoChannel");
    }
    /**
     * redis消息监听器容器
     * 可以添加多个监听不同通道的redis监听器，只需要把消息监听器和相应的消息处理器绑定
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫demoChannel 的通道, 类似redis中的 subscribe 命令
        container.addMessageListener(redisMessageListener, topic());
        return container;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //自定义序列化方式
        RedisSerializer<Object> serializer = new MyRedisSerializer(Object.class);

        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
