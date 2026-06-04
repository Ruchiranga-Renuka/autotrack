package com.example.vehicleservice;

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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VehicleController {

    private final ServiceManager manager;
    private final UserAccountService userAccountService;

    public VehicleController(ServiceManager manager, UserAccountService userAccountService) {
        this.manager = manager;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/vehicles")
    public String vehicles(Model model) {
        model.addAttribute("vehicles", manager.listVehicles());
        model.addAttribute("records", manager.listServiceRecords());
        return "vehicles";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginAction", "/login");
        model.addAttribute("pageTitle", "Login - Vehicle Service Management");
        model.addAttribute("viewTitle", "Login");
        return "login";
    }

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("loginAction", "/login");
        model.addAttribute("pageTitle", "Admin Login - Vehicle Service Management");
        model.addAttribute("viewTitle", "Admin Login");
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

        if (userAccountService.usernameExists(username)) {
            model.addAttribute("error", "That username is already taken. Choose another one.");
            return "register";
        }

        userAccountService.register(username, password, fullName, vehicleNumber, idNumber, telephone, address);

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
        return "redirect:/vehicles";
    }

    @GetMapping("/vehicle/{id}")
    public String vehicleDetails(@PathVariable String id, Model model) {
        Optional<Vehicle> vehicleOpt = manager.findVehicleById(id);
        if (vehicleOpt.isEmpty()) {
            return "redirect:/";
        }
        Vehicle vehicle = vehicleOpt.get();
        List<ServiceRecord> records = manager.listRecordsForVehicle(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("records", records);
        return "vehicle-details";
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
        Optional<Vehicle> vehicleOpt = manager.findVehicleById(vehicleId);
        if (vehicleOpt.isPresent()) {
            return "redirect:/vehicle/" + vehicleId;
        }
        return "redirect:/vehicles";
    }
}
