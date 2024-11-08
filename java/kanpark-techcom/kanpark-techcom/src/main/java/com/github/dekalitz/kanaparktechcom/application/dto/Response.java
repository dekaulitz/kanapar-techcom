package com.github.dekalitz.kanaparktechcom.application.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private MetaResponse meta;
    private T data;
    private ErrorResponse error;

    @Data
    @Getter
    @Setter
    public static class MetaResponse {
        private String code;
        private Long timestamp = System.currentTimeMillis();
        public MetaResponse(String code) {
            this.code = code;
        }
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponse {
        private String code;
        private String message;
    }

}
