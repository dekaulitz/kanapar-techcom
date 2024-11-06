package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.application;

import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {
    private final RepositoryConfiguration repositoryConfiguration;

    @Autowired
    public ServiceConfiguration(RepositoryConfiguration repositoryConfiguration) {
        this.repositoryConfiguration = repositoryConfiguration;
    }
    @Bean
    public UserService getUserService() {
        return new UserServiceImpl(this.repositoryConfiguration.getUserRepository());
    }
}
