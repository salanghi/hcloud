package com.helx.usercenter.kafka;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrapServers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.topics}")
    private List<String> topics;
    @Value("${spring.kafka.consumer.groupId}")
    private String groupId;
    @Value("${spring.kafka.consumer.sessionTimeOut}")
    private String sessionTimeOut;
    @Value("${spring.kafka.consumer.enableAutoCommit}")
    private String enableAutoCommit;
    @Value("${spring.kafka.consumer.autoCommitInterval}")
    private String autoCommitInterval;
    @Value("${spring.kafka.consumer.maxPollRecords}")
    private String maxPollRecords;
    @Value("${spring.kafka.consumer.maxPollInterval}")
    private String maxPollInterval;
    @Value("${spring.kafka.consumer.heartbeatInterval}")
    private String heartbeatInterval;
    @Value("${spring.kafka.consumer.keyDeserializer}")
    private String keyDeserializer;
    @Value("${spring.kafka.consumer.valueDeserializer}")
    private String valueDeserializer;
    @Value("${spring.kafka.consumer.autoOffsetReset}")
    private String autoOffsetReset;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 并发数 多个微服务实例会均分
        factory.setConcurrency(3);
        factory.setBatchListener(true);
        ContainerProperties containerProperties = factory.getContainerProperties();
        // 是否设置手动提交
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> consumerConfigs = consumerConfigs();
        log.info("消费者的配置信息:{}", JSONObject.toJSONString(consumerConfigs));
        return new DefaultKafkaConsumerFactory<>(consumerConfigs);
    }


    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        // 服务器地址
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 是否自动提交
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        // 自动提交间隔
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        //会话时间
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeOut);
        //key序列化
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        //value序列化
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        // 心跳时间
        propsMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);

        // 分组id
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //消费策略
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        // poll记录数
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        //poll时间
        propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        return propsMap;
    }

}

