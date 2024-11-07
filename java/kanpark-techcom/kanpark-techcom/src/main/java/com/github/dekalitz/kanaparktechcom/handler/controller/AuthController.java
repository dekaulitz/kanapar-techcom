package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.*;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.records.ResultRecord;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoLogin;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRefreshToken;
import com.github.dekalitz.kanaparktechcom.application.usecase.auth.DoRegistration;
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
    public ResponseEntity<ResultRecord<ResponseAuthDto>> doLogin(@RequestBody @Valid RequestLoginDto requestLoginDto) throws ApplicationException {
        var authInfo = doLogin.execute(requestLoginDto);
        return ResponseEntity.ok(new ResultRecord<>("OK", authInfo, Collections.emptyList()));
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultRecord<ResponseUserDto>> createUser(@RequestBody @Valid RequestUserDto requestUserDto) throws ApplicationException {
        var userRegistrationResultDto = doRegistration.execute(requestUserDto);
        return ResponseEntity.ok(new ResultRecord<>("OK", userRegistrationResultDto, Collections.emptyList()));
    }

    @PostMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultRecord<ResponseAuthDto>> doRefresh(@RequestBody @Valid RefreshTokenDto refreshTokenDto) throws ApplicationException{
        var authInfo = doRefreshToken.execute(refreshTokenDto);
        return ResponseEntity.ok(new ResultRecord<>("OK", authInfo, Collections.emptyList()));
    }
}
