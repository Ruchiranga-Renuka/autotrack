package com.example.vehicleservice;

public class RegisteredUser {
    private final String id;
    private final String username;
    private final String fullName;
    private final String vehicleNumber;
    private final String idNumber;
    private final String telephone;
    private final String address;

    public RegisteredUser(String id, String username, String fullName, String vehicleNumber, String idNumber, String telephone, String address) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.vehicleNumber = vehicleNumber;
        this.idNumber = idNumber;
        this.telephone = telephone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }
}
