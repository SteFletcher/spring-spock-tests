package com.stefletcher.spring.beans

import org.springframework.data.annotation.Id

import javax.validation.constraints.NotNull


public class User {

    @Id private String id;

    String firstName
    String lastName

    @NotNull(message = "Thou shall populate me!")
    String email
}