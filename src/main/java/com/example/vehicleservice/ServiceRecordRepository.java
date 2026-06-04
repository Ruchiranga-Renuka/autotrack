package com.example.vehicleservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ServiceRecordRepository extends MongoRepository<ServiceRecord, String> {
    List<ServiceRecord> findByVehicleId(String vehicleId);

    List<ServiceRecord> findByServiceDateBetween(LocalDate startDate, LocalDate endDate);
}
