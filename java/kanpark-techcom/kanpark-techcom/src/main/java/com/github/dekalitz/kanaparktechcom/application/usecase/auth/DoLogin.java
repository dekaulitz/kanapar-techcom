package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.AuthDto;
import com.github.dekalitz.kanaparktechcom.application.dto.LoginRequestDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.AuthMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.AcceptPendingException;

@Slf4j
public class DoLogin implements UseCase<AuthDto, LoginRequestDto> {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public DoLogin(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthDto execute(LoginRequestDto dto) throws ApplicationException {
        try {
            UserModel userModel = userRepository.verifyEmailAndPassword(dto.getEmail(), dto.getPassword());
            if (null == userModel) {
                throw new ApplicationException("invalid username or password");
            }
            String newAccessToken = jwtTokenProvider.generateAccessToken(userModel);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userModel.getId());
            return AuthMapper.constructNewAccessInfo(newAccessToken, newRefreshToken, userModel);
        } catch (Exception exception) {
            throw new ApplicationException();
        }
    }
}
