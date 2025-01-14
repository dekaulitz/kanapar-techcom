package com.github.dekalitz.kanaparktechcom.domain.service.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    public T save(T data);

    public Optional<T> findById(String id);

    public List<T> findAll();

    public void deleteById(String id);
}
