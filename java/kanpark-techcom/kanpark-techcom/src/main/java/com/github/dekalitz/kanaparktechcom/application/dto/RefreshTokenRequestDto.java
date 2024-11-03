package com.github.dekalitz.kanaparktechcom.application.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class RefreshTokenRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String refreshToken;
    private String accessToken;
}
