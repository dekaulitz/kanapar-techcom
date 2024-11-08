package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class GetDetailUsers extends BaseCase<ResponseUserDto> implements UseCase<Response<ResponseUserDto>, String> {

    private final UserService userService;

    public GetDetailUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response<ResponseUserDto> execute(String data) throws ApplicationException {
        Optional<UserModel> userModel = userService.findById(data);
        if (userModel.isEmpty()) {
            throw new ApplicationException("data not found");
        }
        return ok(UserMapper.fromUserModel(userModel.get()));
    }
}
