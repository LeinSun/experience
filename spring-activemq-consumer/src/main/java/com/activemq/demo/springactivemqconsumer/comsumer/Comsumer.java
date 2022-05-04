package com.activemq.demo.springactivemqconsumer.comsumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author: sunlei
 * @date: 2022/3/10
 * @description:
 */
@Component
public class Comsumer {
    @JmsListener(destination = "sms.topic")
    public void receiveTopic1(String text) {
        System.out.println("receiveTopic1接收到Topic消息 : " + text);
    }
}
