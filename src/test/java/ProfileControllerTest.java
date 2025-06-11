import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import lt.viko.eif.nSalunov.controller.ProfileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link ProfileController}.
 * <p>
 * Sets up Spring MVC test environment for ProfileController, mocking the {@link UserRepository}.
 * Tests cover retrieving user info by session, updating user profile, and deleting users.
 * </p>
 */

@WebMvcTest(ProfileController.class)
@ContextConfiguration(classes = {ProfileController.class, ProfileControllerTest.MockConfig.class})
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }
    }

    private Users user;
    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        user = new Users();
        user.setId(1);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPhone("123456789");
        user.setRegistrationDate(new Date());

        session = new MockHttpSession();
        session.setAttribute("userID", user.getId());
    }

    @Test
    public void getUserById_shouldReturnUserInfo() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.surname").value(user.getSurname()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    public void updateUser_shouldUpdateFields() throws Exception {
        Map<String, Object> update = new HashMap<>();
        update.put("name", "Updated");
        update.put("surname", "User");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        mockMvc.perform(put("/users/edit")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\",\"surname\":\"User\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_shouldSucceed() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_userNotFound_shouldReturnNotFound() throws Exception {
        when(userRepository.existsById(2)).thenReturn(false);

        mockMvc.perform(delete("/users/2"))
                .andExpect(status().isNotFound());
    }
}
