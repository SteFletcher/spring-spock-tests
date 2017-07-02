package com.stefletcher.spring.controllers

import com.stefletcher.spring.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by stefletcher on 02/07/2017.
 */

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    UserRepository userRepository

    def "spring context loads for web mvc slice"() {

        expect: "controller is available"
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
    }

//    @Autowired
//    UserRepository userRepository;
//
//    def "Test UserRepository initialisation"() {
//        expect: "UserRepository to be autowired into this test spec."
//        userRepository != null
//    }
}
