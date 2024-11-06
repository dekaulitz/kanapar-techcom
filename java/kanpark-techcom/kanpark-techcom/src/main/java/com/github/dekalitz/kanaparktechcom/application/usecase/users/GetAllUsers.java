package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.UserResponseDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GetAllUsers implements UseCase<List<UserResponseDto>, String> {

    private final UserService userService;

    public GetAllUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserResponseDto> execute(String data) throws ApplicationException {
        return userService.findAll().stream().map(UserResponseDto::fromUserModel).toList();
    }
}
