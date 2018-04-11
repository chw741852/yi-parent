package com.yi.d1.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
public class RedisController {
    private final StringRedisTemplate redisTemplate;

    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("set/{kv}")
    public String setKv(@PathVariable String kv) {
        redisTemplate.opsForValue().set(kv, kv, 60, TimeUnit.SECONDS);
        return "true";
    }

    @GetMapping("get/{kv}")
    public String getKv(@PathVariable String kv) {
        return redisTemplate.opsForValue().get(kv);
    }

    @GetMapping("ttl/{kv}")
    public Long ttl(@PathVariable String kv) {
        return redisTemplate.getExpire(kv);
    }

    @GetMapping("del/{kv}")
    public Boolean del(@PathVariable String kv) {
        return redisTemplate.delete(kv);
    }
}
