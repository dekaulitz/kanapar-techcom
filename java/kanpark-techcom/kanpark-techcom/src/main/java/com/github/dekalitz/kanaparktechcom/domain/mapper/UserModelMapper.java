package com.github.dekalitz.kanaparktechcom.domain.mapper;

import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public class UserModelMapper {

    private UserModelMapper() {
    }

    public static UserModel fromUserDto(String id, UserDto data) {
        var userModel = UserModel.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .firstname(data.getFirstname())
                .lastname(data.getLastname())
                .password(data.getPassword())
                .authorities(data.getAuthorities())
                .build();
        userModel.setId(id);
        return userModel;
    }
}
