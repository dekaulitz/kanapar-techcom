package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.redis;

import com.github.dekalitz.kanaparktechcom.domain.repository.cache.AuthCache;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.BaseRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class AuthCacheRepository extends BaseRepository implements AuthCache {
    private final RedisTemplate<String, String> redisTemplate;
    private final static String COLLECTION_NAME = "auth-cache";

    public AuthCacheRepository(RedisTemplate<String, String> redisTemplate) {
        super(COLLECTION_NAME);
        this.redisTemplate = redisTemplate;

    }

    @Override
    public void setCacheAndExpired(String key, String payload, long ttl) {
        redisTemplate.opsForValue().set(getCacheKey(key), payload, ttl, TimeUnit.SECONDS);
    }

    @Override
    public String getCache(String key) {
        return redisTemplate.opsForValue().get(getCacheKey(key));
    }

    @Override
    public void clear(String key) {
        redisTemplate.delete(getCacheKey(key));
    }
}
