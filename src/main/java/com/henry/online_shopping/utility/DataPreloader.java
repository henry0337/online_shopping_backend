package com.henry.online_shopping.utility;

import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.henry.online_shopping.entity.Role.ADMIN;
import static com.henry.online_shopping.entity.Role.USER;

// You may need this if you're about to create admin page for monitoring application.
@Configuration
@RequiredArgsConstructor
public class DataPreloader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            User admin = User.builder()
                    .name("Administrator")
                    .email("admin@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .role(ADMIN)
                    .build();


            User localUser = User.builder()
                    .name("Local user")
                    .email("user@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("user"))
                    .role(USER)
                    .build();

            userRepository.saveAll(List.of(admin, localUser));
        }
    }
}
