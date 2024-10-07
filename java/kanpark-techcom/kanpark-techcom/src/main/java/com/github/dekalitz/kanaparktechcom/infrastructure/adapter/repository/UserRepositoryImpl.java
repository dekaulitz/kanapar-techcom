package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private SingleTonUserDataBase singleTon = SingleTonUserDataBase.getInstance();

    @Override
    public UserModel save(UserModel userModel) {
        userModel.setId(String.valueOf(new Date().getTime()));
        singleTon.getUserModelList().add(userModel);
        return userModel;
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return singleTon.getUserModelList()
                .stream()
                .filter(userModel -> userModel.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<UserModel> findAll() {
        return singleTon.getUserModelList();
    }

    @Override
    public boolean deleteById(String id) {
        return singleTon.getUserModelList()
                .removeIf(model -> model.getId().equals(id));
    }

    @Override
    public boolean isExists(String id) {
        return singleTon.getUserModelList()
                .stream()
                .allMatch(model -> model.getId().equals(id));
    }

    @Override
    public UserModel updateById(String id, UserModel userModel) {
        return singleTon.getUserModelList().stream()
                .filter(model -> model.getId().equals(id))
                .peek(model -> {
                    model.setUserName(userModel.getUserName());
                    model.setEmail(userModel.getEmail());
                    model.setFirstName(userModel.getFirstName());
                    model.setLastName(userModel.getLastName());
                })
                .findFirst()
                .orElse(null);
    }
}
