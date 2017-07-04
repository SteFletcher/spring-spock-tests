package com.stefletcher.spring.beans

import org.springframework.data.annotation.Id


public class User {

    @Id private String id;

    String firstName;
    String lastName;
}