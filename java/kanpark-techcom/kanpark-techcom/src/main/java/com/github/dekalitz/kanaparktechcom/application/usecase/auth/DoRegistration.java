package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoRegistration extends BaseCase<ResponseUserDto> implements UseCase<Response<ResponseUserDto>, RequestUserDto> {

    private final UserService userService;
    private final UserMapper dtoMapper = new UserMapper();

    public DoRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response<ResponseUserDto> execute(RequestUserDto requestUserDto) {
        var email = requestUserDto.getEmail();
        if (this.userService.isEmailExists(email)) {
            log.warn("execute {} already registered", email);
            return failed(ErrorCode.errorOnEmailAlreadyRegistered());
        }
        var userModel = userService.save(dtoMapper.toUserModel(requestUserDto));
        return ok(UserMapper.fromUserModel(userModel));
    }
}
