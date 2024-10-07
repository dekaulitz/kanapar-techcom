package com.github.dekalitz.kanaparktechcom.entrypoint.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.BaseResponse;
import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserDtoMapper;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.api.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<List<UserModel>>> getAll() {
        BaseResponse<List<UserModel>> response = new BaseResponse<>();
        response.setData(userService.findAll());
        response.setStatus("ok");
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserModel>> createUser(@RequestBody @Valid UserDto userDto) {
        UserModel userResponse = userService.save(userDtoMapper.toUserModel(userDto));
        BaseResponse<UserModel> response = new BaseResponse<>();
        response.setData(userResponse);
        response.setStatus("ok");
        return ResponseEntity.ok(response);
    }
}
