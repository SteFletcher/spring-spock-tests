package com.stefletcher.spring.repositories

import com.stefletcher.spring.Application
import com.stefletcher.spring.configuration.TestMongoConfiguration
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import spock.lang.Stepwise

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by stefletcher on 02/07/2017.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = [Application.class, TestMongoConfiguration.class])
@ActiveProfiles("unit")
@Stepwise
class UserRepositorySpec extends Specification {

    @Autowired
    private MockMvc mockMvc


    @Autowired
    UserRepository userRepository

    def "fail to create user when email is missing"() {

        when: "new user is posted without email"
        MvcResult result = mockMvc.perform(post("/user")
                    .content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\" }"))
                    .andReturn()


        then: "400 error returned with expected validation message"
        result.getResponse().getStatus() == 400
        result.getResponse().getContentAsString().contains("Come on now... populate me!")
    }

    def "spring context loads"() {

        when: "ability to put and get users"
        mockMvc.perform(post("/user").content(
                "{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\",\"email\":\"some.email@email.com\"}")).andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("user/")));
        then:
        userRepository.findByLastName("Baggins") != null
        userRepository.findByLastName("Baggins").get(0).firstName == "Frodo"

    }
    def "repository findby AND works expected"() {
        expect:
        userRepository.findByFirstNameAndLastName("Frodo", "Baggins").get(0).firstName == "Frodo"
        cleanup:
        userRepository.deleteAll()
    }

    def "New user created can be retrieved via HATEOS API"() {

        when: "new user created via post"

        mockMvc.perform(post("/user").content(
                ''' 
                            {
                                "firstName": "Frodo", 
                                "lastName":"Baggins",
                                "email":"frodo@bagend.com"
                            }

                    '''))
                .andExpect(status().isCreated()).andExpect(
                header().string("Location", containsString("user/")))

        then: "ability to retrieve the user via the HATEOS API"

        MvcResult response = mockMvc.perform(get("/user/search/findByLastName?name=Baggins")).andReturn()
        response.getResponse().getStatus() == 200
        JsonSlurper slurper = new JsonSlurper()
        def json = slurper.parseText(response.getResponse().getContentAsString())
        json._embedded.user.size() == 1
        json._embedded.user[0].firstName == "Frodo"
        json._embedded.user[0].createdDate != null
    }
}
