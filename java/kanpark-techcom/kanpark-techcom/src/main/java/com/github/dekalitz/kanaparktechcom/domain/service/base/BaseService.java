package com.github.dekalitz.kanaparktechcom.domain.service.base;

import com.github.dekalitz.kanaparktechcom.domain.exception.DomainException;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    public T save(T data);

    public Optional<T> findById(String id);

    public List<T> findAll();

    public void deleteById(String id);

    public T updateById(String id, T data) throws DomainException;
}
