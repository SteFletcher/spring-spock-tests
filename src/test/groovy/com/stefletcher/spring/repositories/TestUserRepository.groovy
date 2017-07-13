package com.stefletcher.spring.repositories

import com.stefletcher.spring.Application
import com.stefletcher.spring.configuration.TestMongoConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by stefletcher on 02/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = [Application.class, TestMongoConfiguration.class])
@ActiveProfiles("unit")
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
                .content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\", \"email\":\"Baggins@bagginsville.com\"}"))
                .andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("user/")));
    }
}
