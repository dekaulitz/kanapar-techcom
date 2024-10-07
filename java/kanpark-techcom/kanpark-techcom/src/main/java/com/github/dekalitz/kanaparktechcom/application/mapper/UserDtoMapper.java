package com.github.dekalitz.kanaparktechcom.application.mapper;

import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public class UserDtoMapper {
    public UserDtoMapper() {
    }

    public UserModel toUserModel(UserDto userDto) {
        return UserModel.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userName(userDto.getUserName())
                .build();
    }
}
