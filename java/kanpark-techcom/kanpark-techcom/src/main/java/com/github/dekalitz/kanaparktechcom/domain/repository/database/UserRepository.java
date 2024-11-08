package com.github.dekalitz.kanaparktechcom.domain.repository.database;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;

public interface UserRepository extends BaseRepository<UserModel> {
     boolean emailIsExists(String email);

     UserModel findByEmail(String email);

     UserModel verifyEmailAndPassword(String email, String password);

    UserModel updateById(String id,UserModel userModel);
}
