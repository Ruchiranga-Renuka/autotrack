package com.example.vehicleservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByVehicleId(String vehicleId);

    List<Booking> findByCustomerId(String customerId);

    List<Booking> findByStatus(BookingStatus status);
}
