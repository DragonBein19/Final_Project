package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserSeeder {

    private final UserRepository userRepository;

    @Autowired
    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void seedUsers() {
        if (userRepository.count() == 0) {
            Users admin = new Users(
                    0,
                    "admin01",
                    "Admin",
                    "User",
                    "admin@example.com",
                    "+37060000001",
                    "admin123", // 👉 patartina naudoti hash tikrame projekte
                    new Date(),
                    true
            );

            Users user = new Users(
                    0,
                    "user01",
                    "Jonas",
                    "Jonaitis",
                    "jonas@example.com",
                    "+37060000002",
                    "password123",
                    new Date(),
                    false
            );

            userRepository.save(admin);
            userRepository.save(user);

            System.out.println("👥 Users seeded.");
        }
    }
}
