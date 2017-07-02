package com.stefletcher.spring.repositories

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by stefletcher on 02/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestUserRepository {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    UserRepository userRepository

    @Test
    public void shouldAutowire() {
        assert userRepository != null
        assert userRepository.findByLastName("fletcher") != null
    }

    @Test
    public void shouldCreateEntityWithoutException() throws Exception {

        mockMvc.perform(post("/user")
                .content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
                .andExpect(
                    status().isCreated()).andExpect(
                    header().string("Location", containsString("user/")));
    }
}
