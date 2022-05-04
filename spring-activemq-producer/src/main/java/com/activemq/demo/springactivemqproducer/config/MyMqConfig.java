package com.activemq.demo.springactivemqproducer.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;


/**
 * @author: sunlei
 * @date: 2022/3/10
 * @description:
 */
@Configuration
public class MyMqConfig {

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("ocp_script_cache");
    }
}
