package com.example.vehicleservice;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAccountService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public void register(String username, String rawPassword, String fullName,
                         String vehicleNumber, String idNumber, String telephone, String address) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        AppUser user = new AppUser(
                UUID.randomUUID().toString(),
                username,
                encodedPassword,
                List.of("USER"),
                fullName,
                vehicleNumber,
                idNumber,
                telephone,
                address
        );
        appUserRepository.save(user);
    }

    public void ensureAdminUser(String username, String rawPassword) {
        if (appUserRepository.existsByUsername(username)) {
            return;
        }
        AppUser admin = new AppUser(
                UUID.randomUUID().toString(),
                username,
                passwordEncoder.encode(rawPassword),
                List.of("USER"),
                "Administrator",
                "",
                "",
                "",
                ""
        );
        appUserRepository.save(admin);
    }
}
