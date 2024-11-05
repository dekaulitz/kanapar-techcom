package com.github.dekalitz.kanaparktechcom.domain.outbound.database;

import com.github.dekalitz.kanaparktechcom.domain.model.BaseModel;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseModel> {
    public T save(T userModel);

    public Optional<T> findById(String id);

    public List<T> findAll();

    public void deleteById(String id);

    public boolean isExists(String id);
}
