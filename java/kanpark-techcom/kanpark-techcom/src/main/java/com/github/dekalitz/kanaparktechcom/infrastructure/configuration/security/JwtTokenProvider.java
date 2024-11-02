package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${auth.configuration.secret.key}")
    private String secretKey;
    @Value("${auth.configuration.token.expiration.time.inMinutes}")
    private long tokenExpirationTimeInMinutes;
    @Value("${auth.configuration.token.refresh.time.inMinutes}")
    private long refreshTimeInMinutes;
    @Value("${auth.configuration.token.tolerance.time.inMinutes}")
    private long toleranceInMinutes;

    public String generateAccessToken(UserModel userModel) throws UnauthorizedException {
        List<GrantedAuthority> grantedAuthorities = userModel.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        JwtClaims claims = new JwtClaims();
        claims.setSubject(userModel.getId());
        claims.setClaim("roles", grantedAuthorities);
        claims.setExpirationTimeMinutesInTheFuture(tokenExpirationTimeInMinutes);
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();
        try {
            return createJwt(claims);
        } catch (JoseException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    public String generateRefreshToken(String accountId) throws UnauthorizedException {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(accountId);
        claims.setExpirationTimeMinutesInTheFuture(refreshTimeInMinutes); // Refresh token berlaku selama 1 hari
        try {
            return createJwt(claims);
        } catch (JoseException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    private String createJwt(JwtClaims claims) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(new org.jose4j.keys.HmacKey(secretKey.getBytes())); // Menggunakan secret key
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256); // Algoritma HMAC SHA-256
        return jws.getCompactSerialization();
    }

    public Boolean validateToken(String jwt) {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // Token harus memiliki waktu kedaluwarsa
                .setAllowedClockSkewInSeconds((int) (toleranceInMinutes * 60)) // Toleransi waktu 30 detik
                .setVerificationKey(new org.jose4j.keys.HmacKey(secretKey.getBytes())) // Menggunakan secret key
                .build();
        try {
            jwtConsumer.processToClaims(jwt);
            return true;
        } catch (InvalidJwtException e) {
            return false;
        }
    }

    public Map<String, Object> getClaims(String jwt) throws UnauthorizedException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(new org.jose4j.keys.HmacKey(secretKey.getBytes()))
                .build();
        JwtClaims claims = null;
        try {
            claims = jwtConsumer.processToClaims(jwt);
            return claims.getClaimsMap();
        } catch (InvalidJwtException e) {
            throw new UnauthorizedException(e.getMessage());
        }

    }
}
