package lt.viko.eif.nSalunov.controller;

import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import lt.viko.eif.nSalunov.controller.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") // leidžia frontend prieiti prie API
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<Users> userOpt = userRepository.findByUserNameAndPassword(
                loginRequest.getUserName(),
                loginRequest.getPassword()
        );

        if (userOpt.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    // ---------- SIGN UP ----------
    @PostMapping("/register") // naudok /register kad aiškiau
    public ResponseEntity<String> registerUser(@RequestBody Users newUser) {
        boolean exists = userRepository.existsByUserName(newUser.getUserName()) ||
                userRepository.existsByEmail(newUser.getEmail());

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username or Email already exists");
        }

        newUser.setRegistrationDate(new Date());
        newUser.setIsAdmin(false);

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
