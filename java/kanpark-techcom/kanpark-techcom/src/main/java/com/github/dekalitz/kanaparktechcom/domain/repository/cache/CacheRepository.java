package com.github.dekalitz.kanaparktechcom.domain.repository.cache;

public interface CacheRepository<T> {
    void setCacheAndExpired(String key, T payload, long ttl);

    T getCache(String key);

    void clear(String key);
}
