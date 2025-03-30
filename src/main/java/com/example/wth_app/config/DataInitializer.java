package com.example.wth_app.config;

import com.example.wth_app.model.Role;
import com.example.wth_app.model.User;
import com.example.wth_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User("admin", passwordEncoder.encode("pw"), Role.ADMIN);
            User user = new User("user", passwordEncoder.encode("pw"), Role.USER);
            userRepository.saveAll(List.of(admin, user));
            System.out.println("Admin and User added to database.");
        }
    }
}
