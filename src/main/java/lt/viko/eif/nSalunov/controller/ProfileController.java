package lt.viko.eif.nSalunov.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //---Naudotojos informacija atvaizdavimas---
    @GetMapping
    public ResponseEntity<?> getUserByIdOrAll(@RequestParam(required = false) Integer id, HttpSession session) {
        id = (int) session.getAttribute("userID");
        if (id != null) {
            Optional<Users> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();
                Map<String, Object> dto = new HashMap<>();
                dto.put("name", user.getName());
                dto.put("surname", user.getSurname());
                dto.put("email", user.getEmail());
                dto.put("phone", user.getPhone());
                dto.put("registrationDate", user.getRegistrationDate());
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
            }
        } else {
            List<Users> users = userRepository.findAll();
            List<Map<String, Object>> result = users.stream().map(user -> {
                Map<String, Object> dto = new HashMap<>();
                dto.put("name", user.getName());
                dto.put("surname", user.getSurname());
                dto.put("email", user.getEmail());
                dto.put("phone", user.getPhone());
                dto.put("registrationDate", user.getRegistrationDate());
                return dto;
            }).toList();
            return ResponseEntity.ok(result);
        }
    }
}
