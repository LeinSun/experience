package com.kafka.demo.springkafkaconsumer.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author: sunlei
 * @date: 2022/3/9
 * @description:
 */
@Component
public class KafkaConsumer {

    //kafka的监听器
    @KafkaListener(topics = "ocp-test", groupId = "ocpGroup")
    public void listenZhugeGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
    }

}
