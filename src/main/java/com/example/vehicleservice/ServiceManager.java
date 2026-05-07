package com.example.vehicleservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServiceManager {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<ServiceRecord> records = new ArrayList<>();

    public Vehicle addVehicle(String make, String model, int year, String ownerName) {
        Vehicle vehicle = new Vehicle(generateId(), make, model, year, ownerName);
        vehicles.add(vehicle);
        return vehicle;
    }

    public Optional<Vehicle> findVehicleById(String vehicleId) {
        return vehicles.stream().filter(v -> v.getId().equals(vehicleId)).findFirst();
    }

    public List<Vehicle> listVehicles() {
        return List.copyOf(vehicles);
    }

    public ServiceRecord addServiceRecord(String vehicleId, LocalDate serviceDate, String description, double cost) {
        ServiceRecord record = new ServiceRecord(generateId(), vehicleId, serviceDate, description, cost);
        records.add(record);
        return record;
    }

    public List<ServiceRecord> listServiceRecords() {
        return List.copyOf(records);
    }

    public List<ServiceRecord> listRecordsForVehicle(String vehicleId) {
        List<ServiceRecord> filtered = new ArrayList<>();
        for (ServiceRecord record : records) {
            if (record.getVehicleId().equals(vehicleId)) {
                filtered.add(record);
            }
        }
        return filtered;
    }

    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
