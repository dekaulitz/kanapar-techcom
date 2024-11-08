package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;


import com.github.dekalitz.kanaparktechcom.domain.repository.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.redis.AuthCacheRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthCacheRepository authCacheRepository;

    public AuthDetailService(UserRepository userRepository, AuthCacheRepository authCacheRepository) {
        this.userRepository = userRepository;
        this.authCacheRepository = authCacheRepository;
    }

    @Override
    public UserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        String accountId = authCacheRepository.getCache(email);
        if (accountId == null) {
            throw new UsernameNotFoundException("invalid token");
        }
        var userModel = userRepository.findById(accountId).orElse(null);
        if (null == userModel) {
            throw new UsernameNotFoundException("invalid token");
        }
        List<SimpleGrantedAuthority> grantedAuthorities = userModel.getAuthorities()
                .stream().map(SimpleGrantedAuthority::new)
                .toList();
        return new UserDetail(userModel.getEmail(), userModel.getPassword(), grantedAuthorities, userModel.getId(), email);
    }
}
