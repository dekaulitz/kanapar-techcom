package com.github.dekalitz.kanaparktechcom.application.usecase.users;

import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserMapper;
import com.github.dekalitz.kanaparktechcom.application.records.UpdateUserUseCaseRecord;
import com.github.dekalitz.kanaparktechcom.application.usecase.BaseCase;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.mapper.UserModelMapper;
import com.github.dekalitz.kanaparktechcom.domain.repository.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateDetailUsers extends BaseCase<ResponseUserDto> implements UseCase<Response<ResponseUserDto>, UpdateUserUseCaseRecord> {

    private final UserService userService;

    private final UserRepository userRepository;

    public UpdateDetailUsers(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Response<ResponseUserDto> execute(UpdateUserUseCaseRecord data) throws ApplicationException {
        var exists = userRepository.isExists(data.userId());
        if (exists) {
            throw new ApplicationException("data not found");
        }
        var userModel = userService.save(UserModelMapper.fromUserDto(data.userId(), data.requestUserDto()));
        return ok(UserMapper.fromUserModel(userModel));
    }
}
