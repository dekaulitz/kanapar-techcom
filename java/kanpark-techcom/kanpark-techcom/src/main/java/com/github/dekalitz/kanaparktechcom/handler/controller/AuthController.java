package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.*;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRefreshToken;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRegistration;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController extends BaseApiController {
    private final JwtTokenProvider jwtTokenProvider;
    private final DoRegistration doRegistration;
    private final DoLogin doLogin;
    private final UserService userService;
    private final DoRefreshToken doRefreshToken;

    public AuthController(HttpServletRequest request, JwtTokenProvider jwtTokenProvider, DoRegistration doRegistration, DoLogin doLogin, UserService userService, DoRefreshToken doRefreshToken) {
        super(request);
        this.jwtTokenProvider = jwtTokenProvider;
        this.doRegistration = doRegistration;
        this.doLogin = doLogin;
        this.userService = userService;
        this.doRefreshToken = doRefreshToken;
    }

    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<AuthDto>> doLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) throws UnauthorizedException, ApplicationException {
        var authInfo = doLogin.execute(loginRequestDto);
        return ResponseEntity.ok(new BaseResponse<>("OK", authInfo, Collections.emptyList()));
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserResultDto>> createUser(@RequestBody @Valid UserDto userDto) throws ApplicationException {
        var userRegistrationResultDto = doRegistration.execute(userDto);
        return ResponseEntity.ok(new BaseResponse<>("OK", userRegistrationResultDto, Collections.emptyList()));
    }

    @PostMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<AuthDto>> doRefresh(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) throws ApplicationException, UnauthorizedException, MalformedClaimException {
        var authInfo = doRefreshToken.execute(refreshTokenRequestDto);
        return ResponseEntity.ok(new BaseResponse<>("OK", authInfo, Collections.emptyList()));
    }

    private AuthDto generateAccessTokenInfo(UserModel userModel) throws UnauthorizedException {
        String newAccessToken = jwtTokenProvider.generateAccessToken(userModel);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userModel.getId());
        return AuthDto.builder()
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .accountId(userModel.getId())
                .authorities(userModel.getAuthorities())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
