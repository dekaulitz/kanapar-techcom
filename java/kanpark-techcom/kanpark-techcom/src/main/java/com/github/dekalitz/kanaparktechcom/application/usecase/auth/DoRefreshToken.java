package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.RefreshTokenDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseAuthDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoRefreshToken extends BaseCase<ResponseAuthDto> implements UseCase<Response<ResponseAuthDto>, RefreshTokenDto> {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public DoRefreshToken(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Response<ResponseAuthDto> execute(RefreshTokenDto dto) throws ApplicationException {
        try {
            var accessToken = dto.getAccessToken();
            var refreshToken = dto.getRefreshToken();
            if (!jwtTokenProvider.isTokenValid(refreshToken)) {
                throw new UnauthorizedException(ErrorCode.errorOnTokenInvalid());
            }
            var claimsAccessToken = jwtTokenProvider.getClaims(accessToken);
            var claimsRefreshToken = jwtTokenProvider.getClaims(refreshToken);
            if (!claimsRefreshToken.getSubject().equals(claimsAccessToken.getSubject())) {
                throw new UnauthorizedException("unauthorized request");
            }
            final UserModel userModel = userService.findById(claimsAccessToken.getSubject()).orElse(null);
            if (null == userModel) {
                throw new UnauthorizedException("unauthorized request");
            }
            jwtTokenProvider.revoke(accessToken,refreshToken);
            String newAccessToken = jwtTokenProvider.generateAccessToken(userModel);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userModel.getId());
            return ok(UserMapper.constructNewAccessInfo(newAccessToken, newRefreshToken, userModel));
        } catch (Exception exception) {
            log.error("doRefreshTokenFailed failed ex", exception);
            return failed(ErrorCode.errorOnTokenInvalid(exception.getMessage()));
        }
    }
}
