package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;

public class UserCacheRepositoryImpl extends UserRepositoryImpl implements UserRepository {

    private final RedisTemplate<String, UserModel> redisTemplate;
    private final static String CACHING_KEY = "user-model:";

    public UserCacheRepositoryImpl(PasswordEncoder passwordEncoder, RedisTemplate<String, UserModel> redisTemplate) {
        super(passwordEncoder);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserModel findByEmail(String email) {
        final String cachingKey = CACHING_KEY + email;
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
