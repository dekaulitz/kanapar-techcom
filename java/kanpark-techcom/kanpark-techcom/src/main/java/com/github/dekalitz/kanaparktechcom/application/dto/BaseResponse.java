package com.github.dekalitz.kanaparktechcom.application.dto;

import java.util.List;

public record BaseResponse<T>(String status, T data, List<String> errors) {
}
