package com.github.dekalitz.kanaparktechcom.handler.controller.filter;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestInfo;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Cast the ServletRequest to HttpServletRequest to access headers
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String accountId = httpRequest.getHeader("x-account-id");
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRemoteAddr(httpRequest.getRemoteAddr());
        requestInfo.setRequestId(request.getRequestId());
        requestInfo.setAccountId(accountId);
        log.info("Request received at {}", requestInfo);
        // Continue the request processing
        request.setAttribute("requestInfo", requestInfo);
        filterChain.doFilter(request, response);
        log.info("Response sent to {}", requestInfo);
    }
}
