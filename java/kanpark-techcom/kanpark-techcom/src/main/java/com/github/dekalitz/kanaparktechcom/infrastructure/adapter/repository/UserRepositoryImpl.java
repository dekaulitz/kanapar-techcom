package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.UserRepository;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.SingletonUserData;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final SingletonUserData<UserModel> singleTon = SingletonUserData.getInstance();

    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel save(UserModel userModel) {
        userModel.setId(String.valueOf(new Date().getTime()));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
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
                    model.setUsername(userModel.getUsername());
                    model.setEmail(userModel.getEmail());
                    model.setFirstName(userModel.getFirstName());
                    model.setLastName(userModel.getLastName());
                })
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean emailIsExists(String email) {
        return singleTon.getUserModelList().stream()
                .anyMatch(userModel -> userModel.getEmail().equals(email));

    }

    @Override
    public UserModel findByEmail(String email) {
        return singleTon.getUserModelList().stream()
                .filter(model -> model.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserModel verifyEmailAndPassword(String email, String password) {
        UserModel userModel = singleTon.getUserModelList().stream()
                .filter(model -> model.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        if (null == userModel || !passwordEncoder.matches(password, userModel.getPassword())) {
            return null;
        }
        return userModel;
    }
}
