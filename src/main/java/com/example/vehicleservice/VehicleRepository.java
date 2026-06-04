package com.example.vehicleservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    List<Vehicle> findByMakeIgnoreCase(String make);

    List<Vehicle> findByModelIgnoreCase(String model);
}
