package com.github.dekalitz.kanaparktechcom.application.usecase.registration;

import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResult;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserDtoMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseUseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistration implements BaseUseCase<UserRegistrationResult, UserDto> {

    private final UserService userService;
    private final UserDtoMapper dtoMapper = new UserDtoMapper();

    public UserRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRegistrationResult execute(UserDto userDto) throws ApplicationException {
        final String email = userDto.getEmail();
        if (this.userService.findByEmail(email)) {
            log.warn("execute {} already registered", email);
            throw new ApplicationException("email already registered");
        }
        UserModel userModel = userService.save(dtoMapper.toUserModel(userDto));
        return UserRegistrationResult.fromUserModel(userModel);
    }
}
