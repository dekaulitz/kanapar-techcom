package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.*;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserDoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.UserRegistration;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.JwtTokenProvider;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController extends BaseApiController {
    private final JwtTokenProvider jwtUtils;
    private final UserRegistration userRegistration;
    private final UserDoLogin userDoLogin;

    public AuthController(HttpServletRequest request, JwtTokenProvider jwtUtils, UserRegistration userRegistration, UserDoLogin userDoLogin) {
        super(request);
        this.jwtUtils = jwtUtils;
        this.userRegistration = userRegistration;
        this.userDoLogin = userDoLogin;
    }

    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<AuthDto>> doLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) throws UnauthorizedException, ApplicationException {
        UserModel userModel = userDoLogin.execute(loginRequestDto);
        String accessToken = jwtUtils.generateAccessToken(userModel);
        String refreshToken = jwtUtils.generateRefreshToken(userModel.getId());
        AuthDto authDto = AuthDto.builder()
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .accountId(userModel.getId())
                .authorities(userModel.getAuthorities())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(new BaseResponse<>("OK", authDto, Collections.emptyList()));
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserRegistrationResultDto>> createUser(@RequestBody @Valid UserDto userDto) throws ApplicationException {
        UserRegistrationResultDto userRegistrationResultDto = userRegistration.execute(userDto);
        BaseResponse<UserRegistrationResultDto> response = new BaseResponse<>("OK", userRegistrationResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserRegistrationResultDto>> doRefresh() throws ApplicationException {
        BaseResponse<UserRegistrationResultDto> response = new BaseResponse<>("OK", null, Collections.emptyList());
        return ResponseEntity.ok(response);
    }
}
