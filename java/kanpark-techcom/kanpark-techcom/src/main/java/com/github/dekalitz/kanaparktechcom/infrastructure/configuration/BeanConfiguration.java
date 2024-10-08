package com.github.dekalitz.kanaparktechcom.infrastructure.configuration;

import com.github.dekalitz.kanaparktechcom.application.usecase.registration.GetDetailRegistration;
import com.github.dekalitz.kanaparktechcom.application.usecase.registration.UserRegistration;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserServiceImpl;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.UserRepositoryImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class BeanConfiguration {
    @Bean
    public UserRepository getUserRepository() {
        return new UserRepositoryImpl();
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
}
