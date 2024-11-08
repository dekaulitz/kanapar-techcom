package com.github.dekalitz.kanaparktechcom.domain.service.userservice;

import com.github.dekalitz.kanaparktechcom.domain.exception.DomainException;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.base.BaseService;

public interface UserService extends BaseService<UserModel> {
    public boolean isEmailExists(String email);

    UserModel updateById(String id, UserModel data) throws DomainException;
}
