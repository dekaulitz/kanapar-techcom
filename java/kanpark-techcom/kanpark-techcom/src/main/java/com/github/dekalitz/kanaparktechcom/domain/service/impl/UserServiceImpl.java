package com.github.dekalitz.kanaparktechcom.domain.service.impl;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.repository.UserRepository;
import com.github.dekalitz.kanaparktechcom.domain.service.api.UserService;

public class UserServiceImpl extends BaseServiceImpl<UserModel> implements UserService {
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }
}
