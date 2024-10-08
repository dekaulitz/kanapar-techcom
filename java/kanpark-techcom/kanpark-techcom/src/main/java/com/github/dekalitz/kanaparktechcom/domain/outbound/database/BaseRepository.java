package com.github.dekalitz.kanaparktechcom.domain.outbound.database;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    public T save(T userModel);

    public Optional<T> findById(String id);

    public List<T> findAll();

    public boolean deleteById(String id);

    public boolean isExists(String id);

    public T updateById(String id, T userModel);
}
