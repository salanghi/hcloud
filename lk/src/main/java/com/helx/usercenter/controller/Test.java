package com.helx.usercenter.controller;

import com.helx.usercenter.util.ILock;
import com.helx.usercenter.util.SimpleRedisLock;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("test")
public class Test {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/sayHello")
    public String sayHello() {
        System.out.println("sayHello");
        return "hello";
    }

    @GetMapping("testRedisLock")
    public void testRedisLock() {
        //获取锁
        ILock lock = new SimpleRedisLock("redisLock", stringRedisTemplate);
        //开始加锁
        try {
            boolean tryLock = lock.tryLock(50);
            if (tryLock){
                System.out.println("程序运行开始");
                Thread.sleep(40000L);
                System.out.println("程序运行结束");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @GetMapping("testRedissonLock")
    public void testRedissonLock() {
        //获取锁
        RLock lock = redissonClient.getLock("lock");
        //开始加锁
        try {
            boolean tryLock = lock.tryLock(100, TimeUnit.SECONDS);
            if (tryLock){
                Thread.sleep(40000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock.isLocked()){
                lock.unlock();
            }
        }
    }

    /**
     * 测试String数据类型
     *
     * @return
     */
    @RequestMapping("/stringTest")
    public String stringTest() {
        this.redisTemplate.delete("name");
        this.redisTemplate.opsForValue().set("name", "路人", 600);
        String name = this.redisTemplate.opsForValue().get("name");
        return name;
    }

    /**
     * 测试List数据类型
     * @return
     */
    @RequestMapping("/listTest")
    public List<String> listTest() {
        this.redisTemplate.delete("names");
        this.redisTemplate.opsForList().rightPushAll("names", "刘德华", "张学友",
                "郭富城", "黎明");
        List<String> courses = this.redisTemplate.opsForList().range("names", 0,
                -1);
        return courses;
    }

    /**
     * 测试set数据类型
     * @return
     */
    @RequestMapping("setTest")
    public Set<String> setTest() {
        this.redisTemplate.delete("courses");
        this.redisTemplate.opsForSet().add("courses", "java", "spring",
                "springboot");
        Set<String> courses = this.redisTemplate.opsForSet().members("courses");
        return courses;
    }

    /**
     * 测试hash数据类型
     * @return
     */
    @RequestMapping("hashTest")
    public Map<Object, Object> hashTest()
    { this.redisTemplate.delete("userMap");
        Map<String, String> map = new HashMap<>();
        map.put("name", "路人");
        map.put("age", "30");
        this.redisTemplate.opsForHash().putAll("userMap",
                map);
        Map<Object, Object> userMap =
                this.redisTemplate.opsForHash().entries("userMap");
        return userMap;
    }

    /**
     * 测试zset数据类型
     * @return
     */
    @RequestMapping("zsetTest")
    public Set<String> zsetTest() {
        this.redisTemplate.delete("languages");
        this.redisTemplate.opsForZSet().add("languages", "java", 100);
        this.redisTemplate.opsForZSet().add("languages", "c++", 95);
        this.redisTemplate.opsForZSet().add("languages", "php", 70);
        this.redisTemplate.opsForZSet().add("languages", "python", 33);
        Set<String> languages =
                this.redisTemplate.opsForZSet().range("languages", 0, -1);
        return languages;
    }
}
