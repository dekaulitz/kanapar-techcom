package com.github.dekalitz.kanaparktechcom.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestInfo implements Serializable {

    private String remoteAddr;
    private String requestId;
    private String accountId;

}
