package com.example.vehicleservice;

public class Vehicle {
    private final String id;
    private final String make;
    private final String model;
    private final int year;
    private final String ownerName;

    public Vehicle(String id, String make, String model, int year, String ownerName) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerName = ownerName;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String toString() {
        return String.format("%s | %s %s %d | Owner: %s", id, make, model, year, ownerName);
    }
}
