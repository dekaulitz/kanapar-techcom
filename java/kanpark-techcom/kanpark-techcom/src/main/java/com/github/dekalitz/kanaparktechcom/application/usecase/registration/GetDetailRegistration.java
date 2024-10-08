package com.github.dekalitz.kanaparktechcom.application.usecase.registration;

import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResult;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserDtoMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseUseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class GetDetailRegistration implements BaseUseCase<UserRegistrationResult, String> {

    private final UserService userService;
    private final UserDtoMapper dtoMapper = new UserDtoMapper();

    public GetDetailRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRegistrationResult execute(String data) throws ApplicationException {
        Optional<UserModel> userModel = userService.findById(data);
        if (userModel.isEmpty()) {
            throw new ApplicationException("data not found");
        }
        return UserRegistrationResult.fromUserModel(userModel.get());
    }
}
