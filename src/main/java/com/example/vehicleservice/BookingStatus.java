package com.example.vehicleservice;

public enum BookingStatus {
    PENDING,
    COMPLETED,
    CANCELLED;

    @Override
    public String toString() {
        return name();
    }
}
