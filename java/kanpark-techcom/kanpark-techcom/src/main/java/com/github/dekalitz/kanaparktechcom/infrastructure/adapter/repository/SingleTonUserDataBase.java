package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository;

import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SingleTonUserDataBase {
    private static SingleTonUserDataBase instance;
    private final List<UserModel> userModelList = new ArrayList<>();

    private SingleTonUserDataBase() {
    }

    public static SingleTonUserDataBase getInstance() {
        if (null == instance) {
            instance = new SingleTonUserDataBase();
        }
        return instance;
    }
}
