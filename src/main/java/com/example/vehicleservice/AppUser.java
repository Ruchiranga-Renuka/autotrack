package com.example.vehicleservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class AppUser {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
    private String fullName;
    private String vehicleNumber;
    private String idNumber;
    private String telephone;
    private String address;

    protected AppUser() {
    }

    public AppUser(String id, String username, String password, List<String> roles,
                   String fullName, String vehicleNumber, String idNumber, String telephone, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
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
