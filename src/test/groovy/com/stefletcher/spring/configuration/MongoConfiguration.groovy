package com.stefletcher.spring.configuration

import com.github.fakemongo.Fongo
import com.mongodb.Mongo
import com.mongodb.MongoClient
import com.stefletcher.spring.Application
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * Created by ste on 09/07/17.
 */
@Configuration
@Profile("unit")
public class TestMongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "development"
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.9", 27017)
    }

}