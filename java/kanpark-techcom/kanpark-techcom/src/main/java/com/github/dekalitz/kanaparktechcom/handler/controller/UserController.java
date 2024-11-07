package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.records.ResultRecord;
import com.github.dekalitz.kanaparktechcom.application.dto.RequestUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetAllUsers;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailUsers;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResultRecord<List<ResponseUserDto>>> getAll() throws ApplicationException {
        ResultRecord<List<ResponseUserDto>> responses = getAllUsers.execute(null);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:READ') or hasAuthority('USER:WRITE')")
    public ResponseEntity<ResultRecord<ResponseUserDto>> getDetailById(@PathVariable String id) throws ApplicationException {
        ResponseUserDto userResultDto = getDetailUsers.execute(id);
        ResultRecord<ResponseUserDto> response = new ResultRecord<>("OK", userResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:WRITE')")
    public ResponseEntity<ResultRecord<ResponseUserDto>> updateById(@PathVariable String id, @RequestBody @Valid RequestUserDto requestUserDto) throws ApplicationException {
        ResponseUserDto userResultDto = getDetailUsers.execute(id);
        ResultRecord<ResponseUserDto> response = new ResultRecord<>("OK", userResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }
}
