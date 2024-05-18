package com.helx.usercenter.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.messaging.Message;

import java.util.concurrent.ExecutionException;

public interface ProducerService {



    /**
     * 发送同步消息
     *
     * @param topic
     * @param data
     * @throws ExecutionException
     * @throws InterruptedException
     */
    void sendSyncMessage(String topic, String data) throws ExecutionException, InterruptedException;

    /**
     * 发送普通消息
     *
     * @param topic
     * @param data
     */
    void sendMessage(String topic, String data);


    /**
     * 发送带附加信息的消息
     *
     * @param record
     */
    void sendMessage(ProducerRecord<String, String> record);


    /**
     * 发送Message消息
     *
     * @param message
     */
    void sendMessage(Message<String> message);


    /**
     * 发送带key的消息
     *
     * @param topic
     * @param key
     * @param data
     */
    void sendMessage(String topic, String key, String data);

    void sendMessage(String topic, Integer partition, String key, String data);


    void sendMessage(String topic, Integer partition, Long timestamp, String key, String data);
}

