package com.demo.sentinel.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunlei
 * @date: 2022/3/9
 * @description:
 */
@RestController
@RequestMapping("kafka")
public class KafkaProducer {
    private final static String TOPIC_NAME = "ocp-test"; //topic的名称

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send")
    public void send() {
        //发送功能就一行代码~
        kafkaTemplate.send(TOPIC_NAME,  "key", "test message send~");
    }
}
