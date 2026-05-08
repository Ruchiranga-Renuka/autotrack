package com.example.vehicleservice;

public enum BookingStatus {
    PENDING,
    COMPLETED;

    @Override
    public String toString() {
        return name();
    }
}
