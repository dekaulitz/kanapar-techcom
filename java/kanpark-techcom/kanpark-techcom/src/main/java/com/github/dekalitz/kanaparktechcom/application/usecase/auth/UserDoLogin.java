package com.github.dekalitz.kanaparktechcom.application.usecase.auth;

import com.github.dekalitz.kanaparktechcom.application.dto.LoginRequestDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.mapper.UserDtoMapper;
import com.github.dekalitz.kanaparktechcom.application.usecase.UseCase;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class UserDoLogin implements UseCase<UserModel, LoginRequestDto> {
    private final UserRepository userRepository;
    public UserDoLogin( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel execute(LoginRequestDto dto) throws ApplicationException {
        UserModel userModel = userRepository.verifyEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (null == userModel) {
            throw new ApplicationException("invalid username or password");
        }
        return userModel;
    }
}
