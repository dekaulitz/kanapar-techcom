package com.github.dekalitz.kanaparktechcom.application.mapper;

import com.github.dekalitz.kanaparktechcom.application.dto.AuthDto;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public class AuthMapper {
    private AuthMapper() {
    }
    public static AuthDto constructNewAccessInfo(String accessToken, String refreshToken, UserModel userModel) {
        return AuthDto.builder()
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .accountId(userModel.getId())
                .authorities(userModel.getAuthorities())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
