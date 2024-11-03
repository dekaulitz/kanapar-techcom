package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.*;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserDoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserRegistration;
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
    private final UserRegistration userRegistration;
    private final UserDoLogin userDoLogin;
    private final UserService userService;

    public AuthController(HttpServletRequest request, JwtTokenProvider jwtTokenProvider, UserRegistration userRegistration, UserDoLogin userDoLogin, UserService userService) {
        super(request);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRegistration = userRegistration;
        this.userDoLogin = userDoLogin;
        this.userService = userService;
    }

    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<AuthDto>> doLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) throws UnauthorizedException, ApplicationException {
        UserModel userModel = userDoLogin.execute(loginRequestDto);
        return ResponseEntity.ok(new BaseResponse<>("OK", generateAccessTokenInfo(userModel), Collections.emptyList()));
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserRegistrationResultDto>> createUser(@RequestBody @Valid UserDto userDto) throws ApplicationException {
        UserRegistrationResultDto userRegistrationResultDto = userRegistration.execute(userDto);
        BaseResponse<UserRegistrationResultDto> response = new BaseResponse<>("OK", userRegistrationResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<AuthDto>> doRefresh(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) throws ApplicationException, UnauthorizedException, MalformedClaimException {
        final String accessToken = refreshTokenRequestDto.getAccessToken();
        final String refreshToken = refreshTokenRequestDto.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("unauthorized request");
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
        return ResponseEntity.ok(new BaseResponse<>("OK", generateAccessTokenInfo(userModel), Collections.emptyList()));
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
