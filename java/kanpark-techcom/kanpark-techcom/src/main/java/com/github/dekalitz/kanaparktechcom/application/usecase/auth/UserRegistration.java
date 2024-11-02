package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResultDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserDtoMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistration implements UseCase<UserRegistrationResultDto, UserDto> {

    private final UserService userService;
    private final UserDtoMapper dtoMapper = new UserDtoMapper();

    public UserRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRegistrationResultDto execute(UserDto userDto) throws ApplicationException {
        var email = userDto.getEmail();
        if (this.userService.findByEmail(email)) {
            log.warn("execute {} already registered", email);
            throw new ApplicationException("email already registered");
        }
        var userModel = userService.save(dtoMapper.toUserModel(userDto));
        return UserRegistrationResultDto.fromUserModel(userModel);
    }
}
