package com.example.vehicleservice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
    public String addVehicle(@RequestParam String make, @RequestParam String model,
                           @RequestParam int year, @RequestParam String ownerName) {
        manager.addVehicle(make, model, year, ownerName);
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