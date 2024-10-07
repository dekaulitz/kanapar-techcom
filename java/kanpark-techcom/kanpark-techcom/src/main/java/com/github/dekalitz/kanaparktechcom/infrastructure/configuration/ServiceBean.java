package com.github.dekalitz.kanaparktechcom.infrastructure.configuration;

import com.github.dekalitz.kanaparktechcom.domain.repository.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.api.UserService;
import com.github.dekalitz.kanaparktechcom.domain.service.impl.UserServiceImpl;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.UserRepositoryImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ServiceBean {

    @Bean
    public UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public UserService getUserService() {
        return new UserServiceImpl(getUserRepository());
    }
}
