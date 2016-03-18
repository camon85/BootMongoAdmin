package com.camon;

import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

/**
 * Created by jooyong on 2016-02-25.
 */
@Configuration
//@EnableMongoRepositories
public class MongoConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.data.mongodb.uri}")
    private String mongodbDataUri;

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        logger.info("mongodbDataUri: {}", mongodbDataUri);
        return new SimpleMongoDbFactory(new MongoClientURI(mongodbDataUri));
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
