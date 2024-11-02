package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

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
            Map<String, Object> claims = jwtTokenProvider.getClaims(accessToken);
//
//            // Load user details dari service
//            UserDetails userDetails = authDetailService.loadUserByUsername(username);
//
//            // Buat autentikasi berdasarkan token yang valid
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//            // Set autentikasi dalam konteks keamanan
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Lanjutkan ke filter berikutnya
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
