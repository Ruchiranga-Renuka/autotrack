package com.example.vehicleservice;

import java.time.LocalDate;

public class Booking {
    private final String id;
    private final String customerId;
    private final String vehicleId;
    private LocalDate requestedDate;
    private String description;
    private BookingStatus status;

    public Booking(String id, String customerId, String vehicleId, LocalDate requestedDate, String description) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.requestedDate = requestedDate;
        this.description = description;
        this.status = BookingStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | Vehicle: %s | Date: %s | %s | Status: %s", id, customerId, vehicleId, requestedDate, description, status);
    }
}
