package org.ledang.springboot.config;

import lombok.RequiredArgsConstructor;
import org.ledang.springboot.entity.User;
import org.ledang.springboot.enums.Role;
import org.ledang.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setEmail("admin@gmail.com");
                admin.setFullName("Admin");
                admin.setPhone("0123456789");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setEmail("user@gmail.com");
                user.setFullName("Le Dang");
                user.setPhone("0987654321");
                user.setRole(Role.USER);
                userRepository.save(user);
            }
        };
    }
}
