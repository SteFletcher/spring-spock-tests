package com.stefletcher.spring

import com.stefletcher.spring.beans.UserValidator
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UserValidator beforeCreateUserValidator(){
        return new UserValidator()
    }

    @Bean
    public UserValidator beforeSaveUserValidator() {
        return new UserValidator();
    }
}
