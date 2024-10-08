package com.github.dekalitz.kanaparktechcom.domain.service.userservice;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.base.BaseService;

public interface UserService extends BaseService<UserModel> {
    public boolean findByEmail(String email);
}
