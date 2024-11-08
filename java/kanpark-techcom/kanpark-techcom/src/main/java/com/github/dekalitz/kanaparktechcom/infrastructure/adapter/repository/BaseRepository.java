package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository;

public class BaseRepository {
    private final String collectionName;

    public BaseRepository(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCacheKey(String key) {
        return this.collectionName + ":" + key;
    }

}
