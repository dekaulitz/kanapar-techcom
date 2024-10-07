package com.github.dekalitz.kanaparktechcom.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    private String id;
    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
