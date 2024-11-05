package com.github.dekalitz.kanaparktechcom.infrastructure.adapter.repository.mongodb;

import com.github.dekalitz.kanaparktechcom.domain.model.BaseModel;
import com.github.dekalitz.kanaparktechcom.domain.outbound.database.BaseRepository;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BaseMongoRepository<T extends BaseModel> implements BaseRepository<T> {
    private final MongoTemplate mongoTemplate;
    protected final String collectionName;
    protected Class<T> entityClass;

    public BaseMongoRepository(MongoTemplate mongoTemplate, String collectionName, Class<T> entityClass) {
        this.mongoTemplate = mongoTemplate;
        this.collectionName = collectionName;
        this.entityClass = entityClass;
    }

    @Override
    public T save(T data) {
        if (null == data.getCreatedAt()) {
            data.setCreatedAt(new Date());
        }
        data.setUpdatedAt(new Date());
        return mongoTemplate.save(data, collectionName);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(new ObjectId(id), this.entityClass, this.collectionName));
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(this.entityClass, this.collectionName);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        DeleteResult result = mongoTemplate.remove(query, this.entityClass, this.collectionName);
        result.wasAcknowledged();
    }

    @Override
    public boolean isExists(String id) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return mongoTemplate.exists(query, this.entityClass, this.collectionName);
    }
}
