package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.RefreshTokenDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoLogout extends BaseCase<Object> implements UseCase<Response<Object>, RefreshTokenDto> {
    private final JwtTokenProvider jwtTokenProvider;

    public DoLogout(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Response<Object> execute(RefreshTokenDto dto) throws ApplicationException {
        jwtTokenProvider.revoke(dto.getAccessToken(), dto.getRefreshToken());
        return ok(null);
    }
}
