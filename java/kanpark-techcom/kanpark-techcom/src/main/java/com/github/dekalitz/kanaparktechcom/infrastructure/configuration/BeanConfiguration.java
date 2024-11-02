package com.github.dekalitz.kanaparktechcom.infrastructure.configuration;

import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserDoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserRegistration;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailRegistration;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserServiceImpl;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.UserRepositoryImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootConfiguration
public class BeanConfiguration {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserRepository getUserRepository() {
        return new UserRepositoryImpl(getPasswordEncoder());
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
