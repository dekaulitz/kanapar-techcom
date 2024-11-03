package com.github.dekalitz.kanaparktechcom.application.mapper;

import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public class UserDtoMapper {
    public UserDtoMapper() {
    }
    public UserModel toUserModel(UserDto userDto) {
        return UserModel.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .password(userDto.getPassword())
                .authorities(userDto.getAuthorities())
                .build();
    }
}
