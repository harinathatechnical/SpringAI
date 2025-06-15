package com.example.SpringAI.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MongoService {
    private static final Logger logger = LoggerFactory.getLogger(MongoService.class);

    private final MongoClient mongoClient;

    /**
     * Initializes the MongoDB client with the given URI.
     */
    public MongoService(@Value("${mongodb.uri:mongodb://localhost:27017}") String mongoUri) {
        logger.info("Initializing MongoServiceClient with URI: {}", mongoUri);
        this.mongoClient = MongoClients.create(mongoUri);
    }

    public MongoClient getMongoClient() {
        return  mongoClient;
    }
}
