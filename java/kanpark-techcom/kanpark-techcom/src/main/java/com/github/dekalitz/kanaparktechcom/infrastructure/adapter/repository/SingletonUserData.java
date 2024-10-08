package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SingletonUserData {
    private static SingletonUserData instance;
    private final List<UserModel> userModelList = new ArrayList<>();

    private SingletonUserData() {
    }

    public static SingletonUserData getInstance() {
        if (null == instance) {
            instance = new SingletonUserData();
        }
        return instance;
    }
}
