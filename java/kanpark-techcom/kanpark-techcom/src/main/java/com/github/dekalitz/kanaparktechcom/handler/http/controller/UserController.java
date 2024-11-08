package com.github.dekalitz.kanaparktechcom.handler.http.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetAllUsers;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailUsers;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import com.github.dekalitz.kanaparktechcom.handler.http.BaseApiController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response<List<ResponseUserDto>>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(getAllUsers.execute(null));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:READ') or hasAuthority('USER:WRITE')")
    public ResponseEntity<Response<ResponseUserDto>> getDetailById(@PathVariable String id) throws ApplicationException {
        return ResponseEntity.status(HttpStatus.OK).body(getDetailUsers.execute(id));

    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER:WRITE')")
    public ResponseEntity<Response<ResponseUserDto>> updateById(@PathVariable String id, @RequestBody @Valid RequestUserDto requestUserDto) throws ApplicationException {
        return ResponseEntity.status(HttpStatus.OK).body(getDetailUsers.execute(id));
    }
}
