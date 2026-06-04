package com.example.vehicleservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "service_records")
public class ServiceRecord {
    @Id
    private String id;
    private String vehicleId;
    private LocalDate serviceDate;
    private String description;
    private double cost;

    protected ServiceRecord() {
    }

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
