package com.github.dekalitz.kanaparktechcom.domain.service.userservice;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.base.BaseServiceImpl;

public class UserServiceImpl extends BaseServiceImpl<UserModel> implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public boolean findByEmail(String email) {
        return null != this.userRepository.findByEmail(email);
    }
}
