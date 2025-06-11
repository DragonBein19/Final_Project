import com.fasterxml.jackson.databind.ObjectMapper;
import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import lt.viko.eif.nSalunov.controller.LoginRequest;
import lt.viko.eif.nSalunov.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, UserControllerTest.MockConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private Users mockUser;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }
    }

    @BeforeEach
    public void setup() {
        mockUser = new Users();
        mockUser.setId(1);
        mockUser.setUserName("admin");
        mockUser.setPassword("pass");
        mockUser.setEmail("admin@example.com");
        mockUser.setName("Admin");
        mockUser.setSurname("Test");
        mockUser.setPhone("123456789");
        mockUser.setRegistrationDate(new Date());
        mockUser.setIsAdmin(true);

        when(userRepository.findByUserNameAndPassword("admin", "pass"))
                .thenReturn(Optional.of(mockUser));

        when(userRepository.findByUserNameAndPassword("wrong", "wrong"))
                .thenReturn(Optional.empty());

        when(userRepository.existsByUserName("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@email.com")).thenReturn(false);
    }

    @Test
    @DisplayName("Should return OK and user data when correct login credentials are provided")
    public void login_ShouldReturnOk_WhenCredentialsCorrect() throws Exception {
        LoginRequest login = new LoginRequest();
        login.setUserName("admin01");
        login.setPassword("admin123");

        Users dbUser = new Users();
        dbUser.setId(1);
        dbUser.setUserName("admin01");
        dbUser.setPassword("admin123");
        dbUser.setEmail("admin@example.com");
        dbUser.setName("Admin");
        dbUser.setSurname("User");
        dbUser.setPhone("+37060000001");
        dbUser.setRegistrationDate(new Date());
        dbUser.setIsAdmin(true);

        when(userRepository.findByUserNameAndPassword("admin01", "admin123"))
                .thenReturn(Optional.of(dbUser));

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("admin01"));
    }

    @Test
    @DisplayName("Should return 401 Unauthorized when wrong login credentials are provided")
    public void login_ShouldReturnUnauthorized_WhenWrongCredentials() throws Exception {
        LoginRequest login = new LoginRequest();
        login.setUserName("wrong");
        login.setPassword("wrong");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    @DisplayName("Should register user successfully when unique username and email are provided")
    public void register_ShouldReturnOk_WhenValid() throws Exception {
        Users newUser = new Users();
        newUser.setUserName("newuser");
        newUser.setPassword("123456");
        newUser.setEmail("new@email.com");
        newUser.setName("Jonas");
        newUser.setSurname("Testas");
        newUser.setPhone("860000000");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }
}
