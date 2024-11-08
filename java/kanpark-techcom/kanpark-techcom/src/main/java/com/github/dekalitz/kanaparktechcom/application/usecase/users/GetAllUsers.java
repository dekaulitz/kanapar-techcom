package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GetAllUsers extends BaseCase<List<ResponseUserDto>> implements UseCase<Response<List<ResponseUserDto>>, String> {

    private final UserService userService;

    public GetAllUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response<List<ResponseUserDto>> execute(String data) {
        var result = userService.findAll().stream().map(UserMapper::fromUserModel).toList();
        return ok(result);
    }
}
