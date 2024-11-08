package com.github.dekalitz.kanaparktechcom.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RequestUserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @NotBlank(message = "username required")
    private String username;
    @NotBlank(message = "email required")
    @Email(message = "email not valid")
    private String email;
    @NotBlank(message = "firstname required")
    private String firstname;
    @NotBlank(message = "lastname required")
    private String lastname;
    @NotBlank(message = "password required")
    private String password;
    private List<String> authorities = new ArrayList<>();
}
