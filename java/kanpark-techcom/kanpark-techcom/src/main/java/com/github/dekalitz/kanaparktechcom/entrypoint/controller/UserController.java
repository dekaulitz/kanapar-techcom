package com.github.dekalitz.kanaparktechcom.entrypoint.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.BaseResponse;
import com.github.dekalitz.kanaparktechcom.application.dto.RequestInfo;
import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResult;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserDtoMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.registration.UserRegistration;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    private final UserRegistration userRegistration;
    private final HttpServletRequest request;

    @Autowired
    public UserController(UserService userService, UserRegistration userRegistration, HttpServletRequest request) {
        this.userService = userService;
        this.userRegistration = userRegistration;
        this.request = request;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<List<UserModel>>> getAll() {
        BaseResponse<List<UserModel>> response = new BaseResponse<>();
        response.setData(userService.findAll());
        response.setStatus("ok");
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserRegistrationResult>> createUser(@RequestBody @Valid UserDto userDto) throws ApplicationException {
        UserRegistrationResult userRegistrationResult = userRegistration.execute(userDto);
        RequestInfo requestInfo = (RequestInfo) request.getAttribute("requestInfo");
        log.info("createUser requested from {}", requestInfo);
        BaseResponse<UserRegistrationResult> response = new BaseResponse<>();
        response.setData(userRegistrationResult);
        response.setStatus("ok");
        return ResponseEntity.ok(response);
    }
}
