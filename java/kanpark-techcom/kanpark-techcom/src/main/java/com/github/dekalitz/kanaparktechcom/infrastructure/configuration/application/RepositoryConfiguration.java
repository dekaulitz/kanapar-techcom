package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.application;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.UserCacheRepositoryImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RepositoryConfiguration {
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public RedisTemplate<String, UserModel> userRedisClient() {
        RedisTemplate<String, UserModel> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserModel.class));
        return template;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserRepository getUserRepository() {
        return new UserCacheRepositoryImpl(getPasswordEncoder(), userRedisClient());
    }

}
