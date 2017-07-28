package com.stefletcher.spring.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.stefletcher.spring.Application
import com.stefletcher.spring.beans.User
import com.stefletcher.spring.configuration.TestMongoConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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

    @Test
    public void shouldNotCreateEntityWithException() throws Exception {

        mockMvc.perform(post("/user")
                .content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\", \"email\":\"\"}"))
                .andExpect(
                status().is4xxClientError());
    }

    /***
     * testing HTTP Put with invalid(Empty) Email
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityWithException() throws Exception {

        User user = new User();
        user.setFirstName("Frodo")
        user.setLastName("Baggins")
        user.setEmail("Baggins@bagginsville.com")
        userRepository.save(user);
        List<User> users=userRepository.findByLastName("Baggins")
        user=users.get(0);
        user.setEmail(""); //Set empty email and try to update the record.Record should not get updated
        mockMvc.perform(put("/user/{id}",user.getId()).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user))).andExpect(status().is4xxClientError())

    }


    /***
     * testing HTTP Patch with invalid(Empty) Email
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityPatchWithException() throws Exception {

        User user = new User();
        user.setFirstName("Frodo")
        user.setLastName("Baggins")
        user.setEmail("Baggins@bagginsville.com")
        userRepository.save(user);
        List<User> users=userRepository.findByLastName("Baggins")
        user=users.get(0);
        mockMvc.perform(patch("/user/{id}",user.getId()).contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"\"}")).andExpect(status().is4xxClientError())
        }
}
