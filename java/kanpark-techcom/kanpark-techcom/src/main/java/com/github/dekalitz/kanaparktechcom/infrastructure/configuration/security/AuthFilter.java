package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import com.github.dekalitz.kanaparktechcom.application.dto.RequestInfo;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthDetailService authDetailService;

    public AuthFilter(JwtTokenProvider jwtTokenProvider, AuthDetailService authDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authDetailService = authDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRemoteAddr(((HttpServletRequest) httpServletRequest).getRemoteAddr());
        requestInfo.setRequestId(httpServletRequest.getRequestId());
        requestInfo.setAccountId("0");
        String accessToken = getJwtFromRequest(httpServletRequest);
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            JwtClaims claims = jwtTokenProvider.getClaims(accessToken);
            try {
                var userDetailInfo = authDetailService.loadUserByUsername(claims.getJwtId());
                UserAuthToken authentication = new UserAuthToken(userDetailInfo, userDetailInfo.getPassword(), userDetailInfo.getAuthorities(), userDetailInfo.getAccountId(), userDetailInfo.getEmail());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                requestInfo.setAccountId(userDetailInfo.getAccountId());
            } catch (MalformedClaimException e) {
                throw new UnauthorizedException(ErrorCode.errorOnTokenInvalid(e.getMessage()));
            }
        }
        httpServletRequest.setAttribute("requestInfo", requestInfo);
        log.info("Request received at {}", requestInfo);
        filterChain.doFilter(httpServletRequest, response);
        log.info("Response sent to {}", requestInfo);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
