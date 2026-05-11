package com.example.vehicleservice;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class VehicleController {

    private final ServiceManager manager = new ServiceManager();
    private final InMemoryUserDetailsManager userDetailsManager;
    private final List<RegisteredUser> registeredUsers = new ArrayList<>();

    public VehicleController(InMemoryUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Vehicle> vehicles = manager.listVehicles();
        List<ServiceRecord> records = manager.listServiceRecords();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("records", records);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String fullName,
            @RequestParam String vehicleNumber,
            @RequestParam String idNumber,
            @RequestParam String telephone,
            @RequestParam String address,
            Model model) {

        if (username.isBlank() || password.isBlank() || fullName.isBlank() || vehicleNumber.isBlank()
                || idNumber.isBlank() || telephone.isBlank() || address.isBlank()) {
            model.addAttribute("error", "Please complete every field before submitting.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match. Please try again.");
            return "register";
        }

        if (userDetailsManager.userExists(username)) {
            model.addAttribute("error", "That username is already taken. Choose another one.");
            return "register";
        }

        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();

        userDetailsManager.createUser(user);
        registeredUsers.add(new RegisteredUser(UUID.randomUUID().toString(), username, fullName, vehicleNumber, idNumber, telephone, address));

        model.addAttribute("success", "Registration complete! You can now log in with your username.");
        return "login";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@RequestParam String Vehicle, @RequestParam String model,
                             @RequestParam int year, @RequestParam String ownerName,
                             @RequestParam("photo") MultipartFile photo) {
        String photoPath = null;
        if (!photo.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Path uploadPath = Paths.get("src/main/resources/static/images");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(photo.getInputStream(), filePath);
                photoPath = "/images/" + fileName;
            } catch (IOException e) {
                // Handle error
            }
        }
        manager.addVehicle(Vehicle, model, year, ownerName, photoPath);
        return "redirect:/";
    }

    @PostMapping("/addServiceRecord")
    public String addServiceRecord(@RequestParam String vehicleId, @RequestParam String serviceDate,
                                   @RequestParam String description, @RequestParam double cost) {
        try {
            LocalDate date = LocalDate.parse(serviceDate);
            manager.addServiceRecord(vehicleId, date, description, cost);
        } catch (DateTimeParseException e) {
            // Handle error
        }
        return "redirect:/";
    }
}