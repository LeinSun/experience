package com.activemq.demo.springactivemqconsumer.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * @author: sunlei
 * @date: 2022/4/28
 * @description:
 */
public class MyRedisSerializer<T> implements RedisSerializer<T> {

    private Class<T> clazz;
    public MyRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }

        return JacksonUtils.toJson(t).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return JacksonUtils.toObject(bytes, clazz);
    }
}
