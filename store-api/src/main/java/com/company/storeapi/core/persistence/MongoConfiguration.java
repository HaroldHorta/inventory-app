package com.company.storeapi.core.persistence;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.PortalPersistenceException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Slf4j
@Configuration
public class MongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String name;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

   @Bean
    public MongoDbFactory mongoDbFactory() {
        try {
            MongoClientOptions.Builder mongoOperations = MongoClientOptions.builder();
            mongoOperations.socketTimeout(1000 * 2);
            mongoOperations.connectTimeout(1000 * 2);
            ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
            MongoClientOptions mo = mongoOperations.build();
            MongoClient mongoClient = new MongoClient(serverAddress, mo);
            return new SimpleMongoDbFactory(mongoClient, name);
        }catch (IllegalArgumentException i){
            throw new PortalPersistenceException(LogRefServices.ERROR_GENERAL_SERVICIO, "Problemas estableciendo la conexión a la base de datos",i);
        }

    }

    public @Bean
    MongoTemplate mongoTemplate() {

        return new MongoTemplate(mongoDbFactory());

    }
}