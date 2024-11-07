package com.github.dekalitz.kanaparktechcom.application.records;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestUserDto;

public record UpdateUserUseCaseRecord(String userId, RequestUserDto requestUserDto) {
}
