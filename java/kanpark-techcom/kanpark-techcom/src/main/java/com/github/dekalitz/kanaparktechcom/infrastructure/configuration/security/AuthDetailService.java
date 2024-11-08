package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;


import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userModel = userRepository.findById(email).orElse(null);
        if (null == userModel) {
            throw new UsernameNotFoundException("invalid token");
        }
        List<SimpleGrantedAuthority> grantedAuthorities = userModel.getAuthorities()
                .stream().map(SimpleGrantedAuthority::new)
                .toList();
        return new User(userModel.getEmail(), userModel.getPassword(), grantedAuthorities);
    }
}
