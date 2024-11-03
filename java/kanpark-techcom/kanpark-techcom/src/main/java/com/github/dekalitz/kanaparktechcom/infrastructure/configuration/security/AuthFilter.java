package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthDetailService authDetailService;

    public AuthFilter(JwtTokenProvider jwtTokenProvider, AuthDetailService authDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authDetailService = authDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getJwtFromRequest(httpServletRequest);
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            JwtClaims claims = jwtTokenProvider.getClaims(accessToken);
            try {
                var userAuthInfo = authDetailService.loadUserByUsername(claims.getSubject());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userAuthInfo, null, userAuthInfo.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                httpServletRequest.setAttribute("x-account-id", claims.getSubject());
            } catch (MalformedClaimException e) {
                throw new UnauthorizedException(e.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
