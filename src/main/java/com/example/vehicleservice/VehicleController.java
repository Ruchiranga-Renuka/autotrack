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
import java.util.UUID;

@Controller
public class VehicleController {

    private final ServiceManager manager = new ServiceManager();

    @GetMapping("/")
    public String home(Model model) {
        List<Vehicle> vehicles = manager.listVehicles();
        List<ServiceRecord> records = manager.listServiceRecords();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("records", records);
        return "home";
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