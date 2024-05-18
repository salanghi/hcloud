package com.helx.usercenter.bloom;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置布隆过滤器
 */
@Configuration
public class BloomFilterConfig {
    @Autowired
    private RedissonClient redissonClient;
    /**
     * 创建订单号布隆过滤器
     * @return
     */
    @Bean
    public RBloomFilter<String> orderBloomFilter() {
        //过滤器名称
        String filterName = "orderBloomFilter";
        // 预期插入数量
        long expectedInsertions = 10000L;
        // 错误比率
        double falseProbability = 0.01;
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(expectedInsertions, falseProbability);
        return bloomFilter;
    }
}

