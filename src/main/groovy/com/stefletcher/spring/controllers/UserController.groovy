package com.stefletcher.spring.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by stefletcher on 02/07/2017.
 */

@RestController
class UserController {

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }

}