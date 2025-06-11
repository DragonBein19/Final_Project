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

            Users user1 = new Users(
                    0,
                    "miau",
                    "Gabriele",
                    "Siaudvytyte",
                    "miau@example.com",
                    "+37060000004",
                    "slap",
                    new Date(),
                    false
            );

            Users user2 = new Users(
                    0,
                    "Inedaa",
                    "Ineda",
                    "Gaigalaite",
                    "ineda@example.com",
                    "+37060000003",
                    "123",
                    new Date(),
                    false
            );

            Users user3 = new Users(
                    0,
                    "Nik",
                    "Nikita",
                    "Sulanovas",
                    "nikita@example.com",
                    "+37060000006",
                    "password",
                    new Date(),
                    false
            );


            userRepository.save(admin);
            userRepository.save(user);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            System.out.println("👥 Users seeded.");
        }
    }
}
