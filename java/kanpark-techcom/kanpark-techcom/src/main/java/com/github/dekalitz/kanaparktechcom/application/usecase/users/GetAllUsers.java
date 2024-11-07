package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.records.ResultRecord;
import com.github.dekalitz.kanaparktechcom.application.responsecode.Result;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GetAllUsers extends Result<List<ResponseUserDto>> implements UseCase<ResultRecord<List<ResponseUserDto>>, String> {

    private final UserService userService;

    public GetAllUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResultRecord<List<ResponseUserDto>> execute(String data) throws ApplicationException {
        var result = userService.findAll().stream().map(ResponseUserDto::fromUserModel).toList();
        return ok(result);
    }
}
