package com.yfkyplatform.parkinglot.configuartion.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Redis 客户端
 *
 * @author Suhuyuan
 */
@Component
public class RedisTool {
    private RedisTemplate redisTemplate;
    @Value("${spring.redis.prefix}")
    private String prefix;

    public RedisTool(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    private<K> String MakeKey(K key){
        return prefix+":"+key;
    }

    public<K ,V> void set(K key,V value){
        redisTemplate.opsForValue().set(MakeKey(key),value);
    }

    public<K ,V> void set(K key, V value, Duration time){
        redisTemplate.opsForValue().set(MakeKey(key),value,time);
    }

    public<K,V> V get(K key){
        return (V)redisTemplate.opsForValue().get(MakeKey(key));
    }

    public<K> boolean check(K key){
        return redisTemplate.hasKey(MakeKey(key));
    }

    public <HK, HV> HashOperations hash(){return redisTemplate.opsForHash();}
}
