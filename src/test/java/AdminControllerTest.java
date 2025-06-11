import com.fasterxml.jackson.databind.ObjectMapper;
import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import lt.viko.eif.nSalunov.controller.AdminController;
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

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = {AdminController.class, AdminControllerTest.MockConfig.class})
public class AdminControllerTest {

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
        mockUser.setSurname("User");
        mockUser.setPhone("123456789");
        mockUser.setRegistrationDate(new Date());
        mockUser.setIsAdmin(true);

        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser));
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(999)).thenReturn(false);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
    }

    @Test
    @DisplayName("Should return list of all users")
    public void getAllUsers_ShouldReturnUserList() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("admin"));
    }

    @Test
    @DisplayName("Should delete user when user exists")
    public void deleteUser_ShouldReturnOk_WhenUserExists() throws Exception {
        mockMvc.perform(delete("/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        verify(userRepository).deleteById(1);
    }

    @Test
    @DisplayName("Should return 404 when trying to delete non-existent user")
    public void deleteUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        mockMvc.perform(delete("/admin/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    @DisplayName("Should update user when user exists")
    public void updateUser_ShouldReturnOk_WhenUserExists() throws Exception {
        Users updated = new Users();
        updated.setUserName("adminUpdated");
        updated.setName("Admin");
        updated.setSurname("Updated");
        updated.setEmail("admin@new.com");
        updated.setPhone("999999999");
        updated.setIsAdmin(false);

        mockMvc.perform(put("/admin/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated"));
    }

    @Test
    @DisplayName("Should return 404 when trying to update non-existent user")
    public void updateUser_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        mockMvc.perform(put("/admin/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}
