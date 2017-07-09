package spring.controllers

import com.stefletcher.spring.Application
import com.stefletcher.spring.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by stefletcher on 02/07/2017.
 */

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = [Application.class])
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

}
