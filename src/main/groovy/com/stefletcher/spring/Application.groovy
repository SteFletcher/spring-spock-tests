package com.stefletcher.spring

import com.stefletcher.spring.beans.UserValidator
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UserValidator beforeCreateUserValidator(){
        return new UserValidator()
    }
}
