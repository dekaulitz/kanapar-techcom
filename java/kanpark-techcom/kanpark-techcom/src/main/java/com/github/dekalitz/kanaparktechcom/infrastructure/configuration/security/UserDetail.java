package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserDetail extends User {

    private String accountId;
    private String email;

    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, String accountId, String email) {
        super(username, password, authorities);
        this.accountId = accountId;
        this.email = email;
    }

    public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
