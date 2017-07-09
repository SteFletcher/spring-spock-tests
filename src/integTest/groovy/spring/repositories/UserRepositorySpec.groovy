package spring.repositories

import com.stefletcher.spring.Application
import com.stefletcher.spring.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Stepwise
import spring.configuration.IntegTestMongoConfiguration

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by stefletcher on 02/07/2017.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = [Application.class, IntegTestMongoConfiguration.class])
@ActiveProfiles("unit")
@Stepwise
class UserRepositorySpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    UserRepository userRepository

    def "spring context loads"() {

        when: "ability to put and get users"
        mockMvc.perform(post("/user").content(
                "{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")).andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("user/")));
        then:
        userRepository.findByLastName("Baggins") != null
        userRepository.findByLastName("Baggins").get(0).firstName == "Frodo"

    }
    def "repository findby AND works expected"() {
        expect:
        userRepository.findByFirstNameAndLastName("Frodo", "Baggins").get(0).firstName == "Frodo"
    }
}
