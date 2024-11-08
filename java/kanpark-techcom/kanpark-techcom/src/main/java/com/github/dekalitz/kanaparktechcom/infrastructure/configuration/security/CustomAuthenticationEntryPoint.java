package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var errCode = ErrorCode.errorOnForbiddenRequest(authException.getMessage());
        var result = new Response<>(new Response.MetaResponse("failed"), null,
                new Response.ErrorResponse(errCode.statusCode(), authException.getMessage()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
