package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.service;

import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserDoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserRegistration;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailRegistration;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserServiceImpl;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.UserCacheRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootConfiguration
public class BeanConfiguration {
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RedisTemplate<String, UserModel> userRedisClient() {
        RedisTemplate<String, UserModel> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserModel.class));
        return template;
    }

    @Bean
    public UserRepository getUserRepository() {
        return new UserCacheRepositoryImpl(getPasswordEncoder(), userRedisClient());
    }

    @Bean
    public UserService getUserService() {
        return new UserServiceImpl(getUserRepository());
    }

    @Bean
    public UserRegistration getUserRegistration() {
        return new UserRegistration(getUserService());
    }

    @Bean
    public GetDetailRegistration getDetailRegistration() {
        return new GetDetailRegistration(getUserService());
    }

    @Bean
    public UserDoLogin getUserDoLogin() {
        return new UserDoLogin(getUserRepository());
    }
}
