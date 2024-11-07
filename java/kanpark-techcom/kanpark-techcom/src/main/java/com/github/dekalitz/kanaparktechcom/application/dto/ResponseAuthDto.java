package com.github.dekalitz.kanaparktechcom.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseAuthDto {
    private String accountId;
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;
    private List<String> authorities;
}
