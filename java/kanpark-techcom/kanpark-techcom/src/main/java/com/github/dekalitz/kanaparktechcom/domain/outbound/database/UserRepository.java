package com.github.dekalitz.kanaparktechcom.domain.outbound.database;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public interface UserRepository extends BaseRepository<UserModel> {
    public boolean emailIsExists(String email);

    public UserModel findByEmail(String email);

    public UserModel verifyEmailAndPassword(String email, String password);

   public UserModel updateById(String id,UserModel userModel);
}
