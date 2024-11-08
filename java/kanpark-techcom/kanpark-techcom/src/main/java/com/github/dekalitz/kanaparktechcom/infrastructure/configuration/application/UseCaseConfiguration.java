package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.application;

import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoLogout;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRefreshToken;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRegistration;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetAllUsers;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailUsers;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.UpdateDetailUsers;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final RepositoryConfiguration repositoryConfiguration;
    private final ServiceConfiguration serviceConfiguration;

    @Autowired
    public UseCaseConfiguration(JwtTokenProvider jwtTokenProvider, RepositoryConfiguration repositoryConfiguration, ServiceConfiguration serviceConfiguration) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.repositoryConfiguration = repositoryConfiguration;
        this.serviceConfiguration = serviceConfiguration;
    }

    @Bean
    public DoLogin doLogin() {
        return new DoLogin(this.repositoryConfiguration.getUserRepository(), jwtTokenProvider);
    }

    @Bean
    public DoRefreshToken doRefreshToken() {
        return new DoRefreshToken(jwtTokenProvider, this.serviceConfiguration.getUserService());
    }

    @Bean
    public DoRegistration doRegistration() {
        return new DoRegistration(this.serviceConfiguration.getUserService());
    }

    @Bean
    public DoLogout doLogout() {
        return new DoLogout(jwtTokenProvider);
    }

    @Bean
    public DoRegistration getUserRegistration() {
        return new DoRegistration(this.serviceConfiguration.getUserService());
    }

    @Bean
    public UpdateDetailUsers getUpdateUserDetail() {
        return new UpdateDetailUsers(this.serviceConfiguration.getUserService(), this.repositoryConfiguration.getUserRepository());
    }

    @Bean
    public GetAllUsers getAllUsers() {
        return new GetAllUsers(this.serviceConfiguration.getUserService());
    }

    @Bean
    public GetDetailUsers getDetailUsers() {
        return new GetDetailUsers(this.serviceConfiguration.getUserService());
    }
}
