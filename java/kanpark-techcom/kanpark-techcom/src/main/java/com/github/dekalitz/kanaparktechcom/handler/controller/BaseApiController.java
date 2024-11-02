package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

public class BaseApiController {
    private final HttpServletRequest request;

    @Autowired
    public BaseApiController(HttpServletRequest request) {
        this.request = request;
    }

    protected RequestInfo getRequestInfo() {
        return (RequestInfo) request.getAttribute("requestInfo");
    }
}
