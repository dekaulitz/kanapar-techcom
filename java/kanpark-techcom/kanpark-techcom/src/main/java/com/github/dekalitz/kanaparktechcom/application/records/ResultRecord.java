package com.github.dekalitz.kanaparktechcom.application.records;

import java.util.List;

public record ResultRecord<T>(String statusCode, String status, T data, List<String> errors) {
}
