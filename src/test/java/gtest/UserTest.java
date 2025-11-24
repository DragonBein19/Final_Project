package gtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import lt.viko.eif.nSalunov.controller.UserController;
import lt.viko.eif.nSalunov.DB.model.Users;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserTest {
        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private UserController userController;

        private MockMvc mockMvc;

        //  Inicializuoja MockMvc prieš kiekvieną testą
        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        }

        // 1. Testuoja prisijungima su teisigais duomenimis, tikisi atsakymo 200 OK
        @Test
        void login_WithValidCredentials_ReturnsOk() throws Exception {
                Users sampleUser = new Users();
                sampleUser.setUserName("john_doe");
                sampleUser.setPassword("password123");

                when(userRepository.findByUserNameAndPassword("john_doe", "password123"))
                        .thenReturn(Optional.of(sampleUser));

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"john_doe\", \"password\":\"password123\"}"))
                        .andExpect(status().isOk());
        }

        // 2. Testuoja prisijungima su neteisingais duomenimis, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithInvalidCredentials_ReturnsUnauthorized() throws Exception {
                when(userRepository.findByUserNameAndPassword("john_doe", "wrongPassword"))
                        .thenReturn(Optional.empty());

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"john_doe\", \"password\":\"wrongPassword\"}"))
                        .andExpect(status().isUnauthorized());
        }

        // 3. Testuoja prisijungima su vartotojo vardu null, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithNullUsername_ReturnsUnauthorized() throws Exception {
                when(userRepository.findByUserNameAndPassword(null, "password123"))
                        .thenReturn(Optional.empty());

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":null, \"password\":\"password123\"}"))
                        .andExpect(status().isUnauthorized());
        }

        // 4. Testuoja prisijungima su vartotojo slaptazodziu null, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithEmptyPassword_ReturnsUnauthorized() throws Exception {
                when(userRepository.findByUserNameAndPassword("john_doe", ""))
                        .thenReturn(Optional.empty());

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"john_doe\", \"password\":\"\"}"))
                        .andExpect(status().isUnauthorized());
        }

        // 5. Testuoja prisijungima su trukstamu slaptazodzio lauku, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithMissingPasswordField_ReturnsUnauthorized() throws Exception {
                when(userRepository.findByUserNameAndPassword("john_doe", null))
                        .thenReturn(Optional.empty());

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"john_doe\"}"))
                        .andExpect(status().isUnauthorized());
        }

        // 6. Testuoja prisijungima su trukstamu vartotojo vardo lauku, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithMissingUsernameField_ReturnsUnauthorized() throws Exception {
                when(userRepository.findByUserNameAndPassword(null, "password123"))
                        .thenReturn(Optional.empty());

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"password123\"}"))
                        .andExpect(status().isUnauthorized());
        }

        // 7. Testuoja prisijungima su tusciu uzklausos kunu, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithEmptyRequestBody_ReturnsUnauthorized() throws Exception {
                when(userRepository.findByUserNameAndPassword(null, null))
                        .thenReturn(Optional.empty());

                mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                        .andExpect(status().isUnauthorized());
        }

        // 8. Testuoja prisijungima su tarpeliais uzpildytu vartotojo vardu, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithWhitespaceUsername_ReturnsUnauthorized() throws Exception {
        when(userRepository.findByUserNameAndPassword("   ", "password123"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"   \", \"password\":\"password123\"}"))
                .andExpect(status().isUnauthorized());
        }

        // 9. Testuoja prisijungima su tarpeliais uzpildytu slaptazodziu, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithWhitespacePassword_ReturnsUnauthorized() throws Exception {
        when(userRepository.findByUserNameAndPassword("john_doe", "   "))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"john_doe\", \"password\":\"   \"}"))
                .andExpect(status().isUnauthorized());
        }

        // 10. Testuoja prisijungima su papildomais nereikalingais laukai JSON'e, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithExtraFieldsInJson_Ignored_ReturnsUnauthorized() throws Exception {
        when(userRepository.findByUserNameAndPassword("john_doe", "wrongPassword"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"john_doe\", \"password\":\"wrongPassword\", \"extra\":\"field\"}"))
                .andExpect(status().isUnauthorized());
        }

        // 11. Testuoja prisijungima su labai ilgu vartotojo vardu, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithLongUsername_ReturnsUnauthorized() throws Exception {
        String longUsername = "user".repeat(50);
        when(userRepository.findByUserNameAndPassword(longUsername, "password123"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"" + longUsername + "\", \"password\":\"password123\"}"))
                .andExpect(status().isUnauthorized());
        }

        // 12. Testuoja prisijungima su SQL injekcijos bandymu vartotojo varde, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithSqlInjectionAttempt_ReturnsUnauthorized() throws Exception {
        String sqlInjection = "' OR '1'='1";
        when(userRepository.findByUserNameAndPassword(sqlInjection, "password123"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"" + sqlInjection + "\", \"password\":\"password123\"}"))
                .andExpect(status().isUnauthorized());
        }

        // 13. Testuoja prisijungima su specialiaisiais simboliais vartotojo varde, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithSpecialCharactersInUsername_ReturnsUnauthorized() throws Exception {
        String specialUsername = "user!@#";
        when(userRepository.findByUserNameAndPassword(specialUsername, "password123"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"" + specialUsername + "\", \"password\":\"password123\"}"))
                .andExpect(status().isUnauthorized());
        }

        // 14. Testuoja prisijungima su labai ilgu slaptažodžiu, tikisi atsakymo 401 Unauthorized
        @Test
        void login_WithVeryLongPassword_ReturnsUnauthorized() throws Exception {
        String longPassword = "p".repeat(500); // labai ilgas slaptažodis
        when(userRepository.findByUserNameAndPassword("john_doe", longPassword))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"john_doe\", \"password\":\"" + longPassword + "\"}"))
                .andExpect(status().isUnauthorized());
        }

        // 15. Testuoja prisijungima su neteisingu JSON formatu, tikisi atsakymo 400 Bad Request
        @Test
        void login_WithJsonMalformed_ReturnsBadRequest() throws Exception {
        String malformedJson = "{\"userName\":\"john_doe\", \"password\":\"password123\""; // trūksta uždarymo }

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
        }

        // 16. Testuoja prisijungima su per didele JSON uzklausa, tikisi atsakymo 400 Bad Request arba 413 Payload Too Large
        @Test
        void login_WithExcessivelyLargeJsonBody_ReturnsBadRequestOrPayloadTooLarge() throws Exception {
        String largeJson = "{\"userName\":\"john_doe\", \"password\":\"" + "p".repeat(10000) + "\"}";

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(largeJson))
                .andExpect(status().is4xxClientError());
        }

        // 17. Testuoja prisijungima naudojant GET metoda vietoj POST, tikisi atsakymo 405 Method Not Allowed
        @Test
        void login_WithGetMethod_ReturnsMethodNotAllowed() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"john_doe\", \"password\":\"password123\"}"))
                .andExpect(status().isMethodNotAllowed());
        }
}
