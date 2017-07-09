package com.stefletcher.spring.configuration

import com.github.fakemongo.Fongo
import com.mongodb.Mongo
import com.mongodb.MongoClient
import com.stefletcher.spring.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * Created by ste on 09/07/17.
 */
@Configuration
@Profile("unit")
public class TestMongoConfiguration extends AbstractMongoConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return "fongo-test";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Fongo(getDatabaseName()).getMongo()
    }


    @Override
    protected String getMappingBasePackage() {
        return "com.stefletcher";
    }
}