package com.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis 设置过期实践最佳实践
 */
@Service
public class RedisExpireBestPractice {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void stringAdd(String key, long liveMinutes, String value) {
        redisTemplate.opsForValue().set(key, value);
        Long expire = redisTemplate.getExpire(key);
        if (Objects.isNull(expire) || expire <= 0L) {
            redisTemplate.expire(key, liveMinutes, TimeUnit.MINUTES);
        }
    }
}
