package com.github.dekalitz.kanaparktechcom.application.dto;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private List<String> authorities = new ArrayList<>();


    public static UserResponseDto fromUserModel(UserModel userModel) {
        return UserResponseDto.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .firstname(userModel.getFirstname())
                .lastname(userModel.getLastname())
                .build();
    }
}
