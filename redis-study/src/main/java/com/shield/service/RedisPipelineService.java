package com.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RedisPipelineService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;  // 用于处理 String 类型的数据

    /**
     * 使用 Pipeline 批量保存 String 数据
     */
    public void saveStringDataWithPipeline(Map<String, String> keyValueMap, final long ttlMills, final TimeUnit timeUnit) {
        if (keyValueMap != null && !keyValueMap.isEmpty()) {
            this.redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                keyValueMap.forEach((k, v) -> connection.set(k.getBytes(StandardCharsets.UTF_8), v.getBytes(StandardCharsets.UTF_8), Expiration.from(ttlMills
                        , timeUnit), RedisStringCommands.SetOption.UPSERT));
                return null;
            });
        }
    }

    /**
     * 使用 Pipeline 批量获取 String 类型数据
     */
    public List<Object> getStringDataWithPipeline(Set<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("Keys list must not be empty.");
        }

        return redisTemplate.executePipelined((RedisCallback<?>) (connection) -> {
            for (String key : keys) {
                connection.get(redisTemplate.getStringSerializer().serialize(key));
            }
            return null;
        });
    }

    /**
     * 使用 Pipeline 批量保存 List 数据
     * @param members
     * @param ttl ms
     */
    public void saveListDataWithPipeline(Map<String, List<String>> members, long ttl) {
        if (members == null || members.isEmpty()) {
            return;
        }
        this.redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            members.forEach((k, v) -> {
                List<byte[]> values = v.stream()
                        .map(m -> m.getBytes(StandardCharsets.UTF_8))
                        .collect(Collectors.toList());
                connection.rPush(k.getBytes(StandardCharsets.UTF_8), values.toArray(new byte[0][0]));
                connection.pExpire(k.getBytes(StandardCharsets.UTF_8), ttl);
            });
            return null;
        });
    }


    public List<Object> getListDataWithPipeline(String listKey, long start, long end) {
        return redisTemplate.executePipelined((RedisCallback<?>) (connection) -> {
            // 批量获取 Redis 列表中的元素
            connection.lRange(redisTemplate.getStringSerializer().serialize(listKey), start, end);
            return null;
        });
    }


    /**
     * 使用 Pipeline 批量获取多个 Redis 键对应的 List 数据
     * @param keys Redis 键的列表
     * @return 批量获取的 List 数据
     */
    public List<Object> getMultipleListsDataWithPipeline(Set<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("Keys list must not be empty.");
        }

        // 使用 RedisTemplate 执行 pipeline
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<?>) (connection) -> {
            // 批量获取 Redis 列表中的元素
            for (String key : keys) {
                // 使用 LRANGE 获取整个 List
                connection.lRange(
                        redisTemplate.getStringSerializer().serialize(key),  // List 的键
                        0,  // 从头开始获取
                        -1  // 获取到 List 的尾部
                );
            }
            return null;
        });

        return objects;
    }


    public void deleteDataWithPipeline(Set<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("Keys list must not be empty.");
        }

        redisTemplate.executePipelined((RedisCallback<?>) (connection) -> {
            for (String key : keys) {
                // 使用 pipeline 批量删除键
                connection.del(redisTemplate.getStringSerializer().serialize(key));
            }
            return null;
        });
    }
}

