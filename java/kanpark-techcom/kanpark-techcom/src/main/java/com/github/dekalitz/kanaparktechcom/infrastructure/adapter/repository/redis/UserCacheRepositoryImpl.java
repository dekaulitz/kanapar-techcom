package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.redis;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.repository.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.UserRepositoryImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;

public class UserCacheRepositoryImpl extends UserRepositoryImpl implements UserRepository {

    private final RedisTemplate<String, UserModel> redisTemplate;

    public UserCacheRepositoryImpl(PasswordEncoder passwordEncoder, RedisTemplate<String, UserModel> redisTemplate) {
        super(passwordEncoder);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserModel findByEmail(String email) {
        final String cachingKey = getCacheKey(email);
        var userModel = redisTemplate.opsForValue().get(cachingKey);
        if (null == userModel) {
            userModel = super.findByEmail(email);
            if (null == userModel) {
                return null;
            }
            redisTemplate.opsForValue().set(cachingKey, userModel, Duration.ofMinutes(15));
        }
        return userModel;
    }
}
