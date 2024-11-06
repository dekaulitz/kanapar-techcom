package com.github.dekalitz.kanaparktechcom.application.dto;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResultDto {
    private String id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;

    public static UserResultDto fromUserModel(UserModel userModel) {
        return UserResultDto.builder()
                .id(userModel.getId())
                .userName(userModel.getUsername())
                .email(userModel.getEmail())
                .firstName(userModel.getFirstname())
                .lastName(userModel.getLastname())
                .build();
    }
}
