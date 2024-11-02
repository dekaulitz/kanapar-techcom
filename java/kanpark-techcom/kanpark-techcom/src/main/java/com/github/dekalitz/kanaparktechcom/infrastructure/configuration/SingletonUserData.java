package com.github.dekalitz.kanaparktechcom.infrastructure.configuration;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SingletonUserData<T> {
    private static SingletonUserData instance;
    private final List<T> userModelList = new ArrayList<>();

    private SingletonUserData() {
    }

    public static SingletonUserData getInstance() {
        if (null == instance) {
            instance = new SingletonUserData();
        }
        return instance;
    }
}
