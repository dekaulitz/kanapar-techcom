package com.github.dekalitz.kanaparktechcom.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @NotBlank(message = "userName required")
    private String userName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
