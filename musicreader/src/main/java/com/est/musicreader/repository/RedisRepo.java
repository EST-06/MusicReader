package com.est.musicreader.repository;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepo implements RedisRepoInterface{
    
    private RedisTemplate<Object, Object> redisTemplate;

    public RedisRepo(RedisTemplate<Object, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        
        this.redisTemplate.setValueSerializer(new StringRedisSerializer());
        this.redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    }

    @Override
    public void saveWithTimeout(String name, Object object, int TimeExp) {
        redisTemplate.opsForValue().set(name, object, Duration.ofSeconds(TimeExp));
    }

    @Override
    public String get(String name) {
        return redisTemplate.opsForValue().get(name).toString();
        
    }

    @Override
    public void save(String name, Object object) {
         redisTemplate.opsForValue().set(name, object);
    }

    

}
