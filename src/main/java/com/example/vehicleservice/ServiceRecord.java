package com.example.vehicleservice;

import java.time.LocalDate;

public class ServiceRecord {
    private final String id;
    private final String vehicleId;
    private final LocalDate serviceDate;
    private final String description;
    private final double cost;

    public ServiceRecord(String id, String vehicleId, LocalDate serviceDate, String description, double cost) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.serviceDate = serviceDate;
        this.description = description;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%s | Date: %s | Vehicle ID: %s | %s | Cost: %.2f", id, serviceDate, vehicleId, description, cost);
    }
}
