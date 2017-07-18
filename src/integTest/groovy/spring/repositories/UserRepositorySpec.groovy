package spring.repositories

import com.stefletcher.spring.Application
import com.stefletcher.spring.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import spock.lang.Stepwise
import spring.configuration.IntegTestMongoConfiguration

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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

    def "New user created can be retrieved via repository"() {

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
        then: "ability to retrieve the user via the repository"
        userRepository.findByLastName("Baggins") != null
        userRepository.findByLastName("Baggins").get(0).firstName == "Frodo"

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
    }
}
