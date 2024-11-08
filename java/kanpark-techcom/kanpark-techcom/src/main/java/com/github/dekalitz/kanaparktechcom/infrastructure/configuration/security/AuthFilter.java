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
import org.jose4j.jwt.consumer.InvalidJwtException;
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
        requestInfo.setRemoteAddr((httpServletRequest).getRemoteAddr());
        requestInfo.setRequestId(httpServletRequest.getRequestId());
        requestInfo.setAccountId("0");
        String accessToken = getJwtFromRequest(httpServletRequest);
        if (accessToken != null) {
            try {
                final Boolean isTokenValid = jwtTokenProvider.isTokenValid(accessToken);
                if (isTokenValid) {
                    JwtClaims claims = jwtTokenProvider.getClaims(accessToken);
                    var userDetailInfo = authDetailService.loadUserByUsername(claims.getJwtId());
                    UserAuthToken authentication = new UserAuthToken(userDetailInfo, userDetailInfo.getPassword(), userDetailInfo.getAuthorities(), userDetailInfo.getAccountId(), userDetailInfo.getEmail());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    requestInfo.setAccountId(userDetailInfo.getAccountId());
                } else {
                    throw new UnauthorizedException(ErrorCode.errorOnTokenInvalid());
                }
            } catch (MalformedClaimException e) {
                throw new UnauthorizedException(ErrorCode.errorOnTokenInvalid());
            } catch (InvalidJwtException e) {
                if (e.hasExpired()) {
                    throw new UnauthorizedException(ErrorCode.errorOnTokenExpired("token expired"));
                }
                throw new UnauthorizedException(ErrorCode.errorOnTokenInvalid());
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
