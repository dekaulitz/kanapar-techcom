package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.BaseResponse;
import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.UserResultDto;
import com.github.dekalitz.kanaparktechcom.application.dto.UserResponseDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetAllUsers;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailUsers;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseApiController {
    private final UserService userService;

    private final GetDetailUsers getDetailUsers;
    private final GetAllUsers getAllUsers;

    @Autowired
    public UserController(UserService userService, GetDetailUsers getDetailUsers, HttpServletRequest request, GetAllUsers getAllUsers) {
        super(request);
        this.userService = userService;
        this.getDetailUsers = getDetailUsers;
        this.getAllUsers = getAllUsers;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:READ') or hasAuthority('USER:WRITE')")
    public ResponseEntity<BaseResponse<List<UserResponseDto>>> getAll() throws ApplicationException {
        List<UserResponseDto> responses = getAllUsers.execute(null);
        return ResponseEntity.ok(new BaseResponse<>("OK", responses, Collections.emptyList()));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:READ') or hasAuthority('USER:WRITE')")
    public ResponseEntity<BaseResponse<UserResultDto>> getDetailById(@PathVariable String id) throws ApplicationException {
        UserResultDto userResultDto = getDetailUsers.execute(id);
        BaseResponse<UserResultDto> response = new BaseResponse<>("OK", userResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:WRITE')")
    public ResponseEntity<BaseResponse<UserResultDto>> updateById(@PathVariable String id, @RequestBody @Valid UserDto userDto) throws ApplicationException {

        UserResultDto userResultDto = getDetailUsers.execute(id);
        BaseResponse<UserResultDto> response = new BaseResponse<>("OK", userResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }
}
