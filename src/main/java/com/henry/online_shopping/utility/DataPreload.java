package com.henry.online_shopping.utility;

import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.henry.online_shopping.entity.Role.ADMIN;
import static com.henry.online_shopping.entity.Role.USER;

@Configuration
@RequiredArgsConstructor
public class DataPreload {

    private final UserRepository userRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            final List<User> currentListOfAdminAccount = userRepository.findByRole(ADMIN);
            final List<User> currentListOfUserAccount = userRepository.findByRole(USER);

            if (currentListOfAdminAccount.isEmpty()) {
                User admin = User.builder()
                        .name("Administrator")
                        .email("admin@gmail.com")
                        .password(new BCryptPasswordEncoder().encode("admin"))
                        .role(ADMIN)
                        .build();

                userRepository.save(admin);
            }

            if (currentListOfUserAccount.isEmpty()) {
                User localUser = User.builder()
                        .name("Local user")
                        .email("user@gmail.com")
                        .password(new BCryptPasswordEncoder().encode("user"))
                        .role(USER)
                        .build();

                userRepository.save(localUser);
            }
        };
    }
}
