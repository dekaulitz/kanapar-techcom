package com.github.dekalitz.kanaparktechcom.domain.service.base;

import com.github.dekalitz.kanaparktechcom.domain.model.BaseModel;
import com.github.dekalitz.kanaparktechcom.domain.repository.database.BaseRepository;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {
    private final BaseRepository<T> repository;

    public BaseServiceImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T data) {
        return repository.save(data);
    }

    @Override
    public Optional<T> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
