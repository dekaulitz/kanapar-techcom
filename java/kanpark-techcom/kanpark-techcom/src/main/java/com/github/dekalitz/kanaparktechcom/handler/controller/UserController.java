package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.BaseResponse;
import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResultDto;
import com.github.dekalitz.kanaparktechcom.application.dto.UserResponseDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailUsers;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseApiController {
    private final UserService userService;

    private final GetDetailUsers getDetailUsers;

    @Autowired
    public UserController(UserService userService, GetDetailUsers getDetailUsers, HttpServletRequest request) {
        super(request);
        this.userService = userService;
        this.getDetailUsers = getDetailUsers;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:READ') or hasRole('USER:WRITE')")
    public ResponseEntity<BaseResponse<List<UserResponseDto>>> getAll() {
        List<UserResponseDto> responses = userService.findAll().stream().map(userModel ->
                UserResponseDto.builder()
                        .id(userModel.getId())
                        .email(userModel.getEmail())
                        .firstname(userModel.getFirstname())
                        .lastname(userModel.getLastname())
                        .username(userModel.getUsername())
                        .authorities(userModel.getAuthorities())
                        .build()).toList();
        BaseResponse<List<UserResponseDto>> response = new BaseResponse<>("OK", responses, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:READ') or hasRole('USER:WRITE')")
    public ResponseEntity<BaseResponse<UserRegistrationResultDto>> getDetailRegistration(@PathVariable String id) throws ApplicationException {
        UserRegistrationResultDto userRegistrationResultDto = getDetailUsers.execute(id);
        BaseResponse<UserRegistrationResultDto> response = new BaseResponse<>("OK", userRegistrationResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }


}
