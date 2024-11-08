package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserAuthToken extends UsernamePasswordAuthenticationToken {
    @Getter
    private String accountId;
    @Getter
    private String email;

    public UserAuthToken(Object principal, Object credentials,
                         Collection<? extends GrantedAuthority> authorities, String accountId, String email) {
        super(principal, credentials, authorities);
        this.accountId = accountId;
        this.email = email;
    }
}
