package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResultDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class GetDetailRegistration implements UseCase<UserRegistrationResultDto, String> {

    private final UserService userService;

    public GetDetailRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRegistrationResultDto execute(String data) throws ApplicationException {
        Optional<UserModel> userModel = userService.findById(data);
        if (userModel.isEmpty()) {
            throw new ApplicationException("data not found");
        }
        return UserRegistrationResultDto.fromUserModel(userModel.get());
    }
}
