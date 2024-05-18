package com.helx.usercenter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName RedisLockUtil
 * @Description 使用redis做锁
 * @Author WangJing
 * @Date 2020年6月8日
 * @Version V1.1.0
 */
//@Component
public class RedisLockUtil {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    /**
     * 任务锁前缀
     */
    public static final String TASK_LOCK_PREFIX = "t_l_prefix";
    /**
     * 任务锁过期时间 2000毫秒
     */
    public static final Integer TASK_LOCK_EXPIRE = 2000;

    /**
     * 获取锁，true 则得到锁，false 已被锁定
     * @param lockName       锁名称
     * @param lockExoire     锁时间
     * @return
     */
    public Boolean getLock(String lockName, Integer lockExoire) {
        return (Boolean) redisTemplate.execute((RedisCallback<?>) connection -> {
            // 获取时间毫秒值
            long expireAt = System.currentTimeMillis() + lockExoire + 1;
            // 获取锁
            Boolean acquire = connection.setNX(lockName.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] bytes = connection.get(lockName.getBytes());
                // 非空判断
                if (Objects.nonNull(bytes) && bytes.length > 0) {
                    long expireTime = Long.parseLong(new String(bytes));
                    // 如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] set = connection.getSet(lockName.getBytes(),
                                String.valueOf(System.currentTimeMillis() + lockExoire + 1).getBytes());
                        return Long.parseLong(new String(set)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * 删除锁
     * @param lockName
     */
    public void delLock(String lockName) {
        redisTemplate.delete(lockName);
    }

    /**
     * 获取锁Key
     * @param prefix    前缀
     * @param name      名称
     * @return
     */
    public static String getFullKey(String prefix, String name) {
        return prefix + "_" + name;
    }

}