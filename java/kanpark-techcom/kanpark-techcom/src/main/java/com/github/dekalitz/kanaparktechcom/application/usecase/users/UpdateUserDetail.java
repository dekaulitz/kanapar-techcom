package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.UserResultDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.records.UpdateUserRecord;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.mapper.UserModelMapper;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserDetail implements UseCase<UserResultDto, UpdateUserRecord> {

    private final UserService userService;

    private final UserRepository userRepository;

    public UpdateUserDetail(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public UserResultDto execute(UpdateUserRecord data) throws ApplicationException {
        var exists = userRepository.isExists(data.userId());
        if (exists) {
            throw new ApplicationException("data not found");
        }
        var userModel = userService.save(UserModelMapper.fromUserDto(data.userId(), data.userDto()));
        return UserResultDto.fromUserModel(userModel);
    }
}
