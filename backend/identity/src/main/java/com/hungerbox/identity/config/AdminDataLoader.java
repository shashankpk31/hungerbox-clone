package com.hungerbox.identity.config;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hungerbox.identity.constant.IdentityConstantAndEnum.Role;
import com.hungerbox.identity.entity.User;
import com.hungerbox.identity.repository.UserRepository;

@Component
public class AdminDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminDataLoader.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "super@hungerbox.com";
        String adminUsername = "superadmin";

        if (userRepository.findByEmailOrPhoneNumber(adminEmail, adminEmail).isEmpty()) {
            logger.info("No Super Admin found. Creating default admin account...");

            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("Admin@123")); 
            admin.setRole(Role.ROLE_SUPER_ADMIN);
            admin.setIsEmailVerified(true);
            admin.setIsPhoneVerified(true);
            
            admin.setCreatedAt(LocalDateTime.now());
            admin.setCreatedBy("SYSTEM");
            admin.setDeleted(false);

            userRepository.save(admin);
            logger.info("Super Admin created successfully with username: {}", adminUsername);
        } else {
            logger.info("Super Admin already exists in the database.");
        }
    }
}