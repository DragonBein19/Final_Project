package lt.viko.eif.nSalunov.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/users")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //---Naudotojos informacija atvaizdavimas---
    @GetMapping
    public ResponseEntity<?> getUserByIdOrAll(@RequestParam(value = "id", required = false) Integer id, HttpSession session)
    {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return userRepository.findById(id).map(user -> {
            if(updates.containsKey("Name")) {
                user.setName((String) updates.get("Name"));
            }
            if(updates.containsKey("Surname")) {
                user.setSurname((String) updates.get("Surname"));
            }
            if(updates.containsKey("email")) {
                user.setEmail((String) updates.get("email"));
            }
            if(updates.containsKey("phone")) {
                user.setPhone((String) updates.get("phone"));
            }
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Users> cretateUsers(@RequestBody Users users) {
        Users savedUsers = userRepository.save(users);
        return ResponseEntity.ok(savedUsers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable int id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
