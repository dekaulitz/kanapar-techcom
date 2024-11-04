package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
public class MongodbConfig {

    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.server.selection.timeout}")
    private long serverSelectionTimeOut;
    @Value("${spring.data.mongodb.connection-pool.max-size}")
    private int maxPoolSize;
    @Value("${spring.data.mongodb.connection-pool.min-size}")
    private int minPoolSize;
    @Value("${spring.data.mongodb.connection-pool.max-wait-time}")
    private long maxWaitTime;
    @Value("${spring.data.mongodb.connection-pool.max-connection-idle-time}")
    private long maxConnectionIdleTime;
    @Value("${spring.data.mongodb.connection-pool.max-connection-life-time}")
    private long maxConnectionLifeTime;
    @Value("${spring.data.mongodb.socket.connect-timeout}")
    private long connectionTimeOut;
    @Value("${spring.data.mongodb.socket.read-timeout}")
    private long readTimeOut;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(uri);
        // Konfigurasi MongoClientSettings
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(serverSelectionTimeOut, TimeUnit.SECONDS))  // Timeout pemilihan server
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(maxPoolSize)  // Maksimal koneksi di pool
                        .minSize(minPoolSize)   // Minimal koneksi di pool
                        .maxWaitTime(maxWaitTime, TimeUnit.MILLISECONDS)  // Waktu tunggu maksimum
                        .maxConnectionIdleTime(maxConnectionIdleTime, TimeUnit.MILLISECONDS)  // Waktu idle koneksi maksimum
                        .maxConnectionLifeTime(maxConnectionLifeTime, TimeUnit.MILLISECONDS)) // Waktu hidup koneksi maksimum
                .applyToSocketSettings(builder -> builder
                        .connectTimeout(connectionTimeOut, TimeUnit.MILLISECONDS)  // Timeout koneksi socket
                        .readTimeout(readTimeOut, TimeUnit.MILLISECONDS))   // Timeout membaca socket
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), database);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        // Konfigurasi tambahan di MongoTemplate, seperti WriteConcern dan WriteResultChecking
        mongoTemplate.setWriteConcernResolver(action -> WriteConcern.MAJORITY);
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return mongoTemplate;
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new DateToLocalDateTimeConverter(),
                new LocalDateTimeToDateConverter()
        ));
    }

    @Component
    public static class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
        @Override
        public LocalDateTime convert(Date source) {
            return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

    @Component
    public static class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {
        @Override
        public Date convert(LocalDateTime source) {
            return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
        }
    }
}
