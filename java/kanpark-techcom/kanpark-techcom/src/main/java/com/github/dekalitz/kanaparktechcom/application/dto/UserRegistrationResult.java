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
public class UserRegistrationResult {
    private String id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;

    public static UserRegistrationResult fromUserModel(UserModel userModel) {
        return UserRegistrationResult.builder()
                .id(userModel.getId())
                .userName(userModel.getUserName())
                .email(userModel.getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .build();
    }
}
