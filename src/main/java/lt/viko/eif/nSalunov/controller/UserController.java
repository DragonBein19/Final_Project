package lt.viko.eif.nSalunov.controller;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<Users> userOpt = userRepository.findByUserNameAndPassword(
                loginRequest.getUserName(),
                loginRequest.getPassword()
        );

        if (userOpt.isPresent()) {
            session.setAttribute("userID", userOpt.get().getId()); //Išsaugome šios sesijos naudotojo ID
            return ResponseEntity.ok(userOpt.get()); // grąžina visą user'į su isAdmin ir t.t.

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
