package com.example.vehicleservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmailIgnoreCase(String email);

    List<Customer> findByNameContainingIgnoreCase(String name);
}
