package com.github.dekalitz.kanaparktechcom.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String status;
    private T data;
    private List<String> errors;
}
