package com.example.vehicleservice;

public class Vehicle {
    private final String id;
    private String make;
    private String model;
    private int year;
    private String ownerName;
    private String photoPath;

    public Vehicle(String id, String make, String model, int year, String ownerName, String photoPath) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerName = ownerName;
        this.photoPath = photoPath;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return String.format("%s | %s %s %d | Owner: %s", id, make, model, year, ownerName);
    }
}
