package com.github.dekalitz.kanaparktechcom.application.records;

import com.github.dekalitz.kanaparktechcom.application.dto.UserDto;

public record UpdateUserRecord(String userId, UserDto userDto) {
}
