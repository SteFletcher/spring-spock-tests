package com.stefletcher.spring.beans

import org.springframework.data.annotation.Id


public class User {

    @Id private String id;

    private String firstName;
    private String lastName;
}