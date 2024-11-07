package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorRecord;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoRegistration implements UseCase<ResponseUserDto, RequestUserDto> {

    private final UserService userService;
    private final UserMapper dtoMapper = new UserMapper();

    public DoRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseUserDto execute(RequestUserDto requestUserDto) throws ApplicationException {
        var email = requestUserDto.getEmail();
        if (this.userService.findByEmail(email)) {
            log.warn("execute {} already registered", email);
            throw new ApplicationException(ErrorRecord.EMAIL_ALREADY_REGISTERED(email));
        }
        var userModel = userService.save(dtoMapper.toUserModel(requestUserDto));
        return ResponseUserDto.fromUserModel(userModel);
    }
}
