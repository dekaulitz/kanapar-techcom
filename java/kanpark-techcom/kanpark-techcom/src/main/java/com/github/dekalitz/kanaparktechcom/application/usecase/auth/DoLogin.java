package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestLoginDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseAuthDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.repository.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoLogin extends BaseCase<ResponseAuthDto> implements UseCase<Response<ResponseAuthDto>, RequestLoginDto> {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public DoLogin(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Response<ResponseAuthDto> execute(RequestLoginDto dto) {
        try {
            UserModel userModel = userRepository.verifyEmailAndPassword(dto.getEmail(), dto.getPassword());
            if (null == userModel) {
                throw new ApplicationException(ErrorCode.errorOnInvalidLogin());
            }
            String newAccessToken = jwtTokenProvider.generateAccessToken(userModel);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userModel.getId());
            return ok(UserMapper.constructNewAccessInfo(newAccessToken, newRefreshToken, userModel));
        } catch (ApplicationException exception) {
            log.error("doLogin failed ex", exception);
            return failed(exception.getErrorCode());
        }
    }
}

