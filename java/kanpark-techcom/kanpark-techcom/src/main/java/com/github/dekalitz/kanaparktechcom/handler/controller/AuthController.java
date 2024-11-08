package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.*;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRefreshToken;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController extends BaseApiController {
    private final DoRegistration doRegistration;
    private final DoLogin doLogin;
    private final DoRefreshToken doRefreshToken;

    public AuthController(HttpServletRequest request, DoRegistration doRegistration, DoLogin doLogin, DoRefreshToken doRefreshToken) {
        super(request);
        this.doRegistration = doRegistration;
        this.doLogin = doLogin;
        this.doRefreshToken = doRefreshToken;
    }
    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ResponseAuthDto>> doLogin(@RequestBody @Valid RequestLoginDto requestLoginDto) throws ApplicationException {
        return ResponseEntity.ok(doLogin.execute(requestLoginDto));
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ResponseUserDto>> createUser(@RequestBody @Valid RequestUserDto requestUserDto) throws ApplicationException {
        return ResponseEntity.ok( doRegistration.execute(requestUserDto));
    }

    @PostMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ResponseAuthDto>> doRefresh(@RequestBody @Valid RefreshTokenDto refreshTokenDto) throws ApplicationException {
        return ResponseEntity.ok(doRefreshToken.execute(refreshTokenDto));
    }
}
