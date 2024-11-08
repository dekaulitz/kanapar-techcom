package com.github.dekalitz.kanaparktechcom.application.mapper;

import com.github.dekalitz.kanaparktechcom.application.dto.ResponseAuthDto;
import com.github.dekalitz.kanaparktechcom.application.dto.RequestUserDto;
import com.github.dekalitz.kanaparktechcom.application.dto.ResponseUserDto;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public class UserMapper {
    public UserMapper() {
    }
    public UserModel toUserModel(RequestUserDto requestUserDto) {
        return UserModel.builder()
                .username(requestUserDto.getUsername())
                .email(requestUserDto.getEmail())
                .firstname(requestUserDto.getFirstname())
                .lastname(requestUserDto.getLastname())
                .password(requestUserDto.getPassword())
                .authorities(requestUserDto.getAuthorities())
                .build();
    }

    public static ResponseAuthDto constructNewAccessInfo(String accessToken, String refreshToken, UserModel userModel) {
        return ResponseAuthDto.builder()
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .accountId(userModel.getId())
                .authorities(userModel.getAuthorities())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static ResponseUserDto fromUserModel(UserModel userModel) {
        return ResponseUserDto.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .firstname(userModel.getFirstname())
                .lastname(userModel.getLastname())
                .authorities(userModel.getAuthorities())
                .build();
    }
}
