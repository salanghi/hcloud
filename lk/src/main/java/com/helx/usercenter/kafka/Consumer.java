package com.helx.usercenter.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Consumer {

    //    @KafkaListener(topics = {"${spring.kafka.consumer.topics}"},
//                    groupId = "${spring.kafka.consumer.groupId}",
//                    containerFactory = "kafkaListenerContainerFactory",
//                    properties = {"${spring.kafka.consumer.autoOffsetReset}"})
    @KafkaListener(topics = {"#{T(java.util.Arrays).asList('${spring.kafka.consumer.topics}'.split(','))}"},
            groupId = "${spring.kafka.consumer.groupId}",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "1",
            properties = {"${spring.kafka.consumer.autoOffsetReset}"})
    public void topicTest(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        for (ConsumerRecord<String, String> record : records) {
            log.info("topic_test 消费了： Topic:" + record.topic() + ",Message:" + record.value());
            //手动提交偏移量
            ack.acknowledge();

        }
    }

}


