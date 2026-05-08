package com.example.vehicleservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServiceManager {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<ServiceRecord> records = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public Vehicle addVehicle(String make, String model, int year, String ownerName) {
        Vehicle vehicle = new Vehicle(generateId(), make, model, year, ownerName);
        vehicles.add(vehicle);
        return vehicle;
    }

    public Optional<Vehicle> findVehicleById(String vehicleId) {
        return vehicles.stream().filter(v -> v.getId().equals(vehicleId)).findFirst();
    }

    public List<Vehicle> listVehicles() {
        return List.copyOf(vehicles);
    }

    public Customer addCustomer(String name, String email, String phone) {
        Customer customer = new Customer(generateId(), name, email, phone);
        customers.add(customer);
        return customer;
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return customers.stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public List<Customer> listCustomers() {
        return List.copyOf(customers);
    }

    public Booking addBooking(String customerId, String vehicleId, LocalDate requestedDate, String description) {
        Booking booking = new Booking(generateId(), customerId, vehicleId, requestedDate, description);
        bookings.add(booking);
        return booking;
    }

    public Optional<Booking> findBookingById(String bookingId) {
        return bookings.stream().filter(b -> b.getId().equals(bookingId)).findFirst();
    }

    public List<Booking> listBookings() {
        return List.copyOf(bookings);
    }

    public List<Booking> listBookingsForVehicle(String vehicleId) {
        List<Booking> filtered = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getVehicleId().equals(vehicleId)) {
                filtered.add(booking);
            }
        }
        return filtered;
    }

    public List<Booking> listBookingsForCustomer(String customerId) {
        List<Booking> filtered = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(customerId)) {
                filtered.add(booking);
            }
        }
        return filtered;
    }

    public ServiceRecord addServiceRecord(String vehicleId, LocalDate serviceDate, String description, double cost) {
        ServiceRecord record = new ServiceRecord(generateId(), vehicleId, serviceDate, description, cost);
        records.add(record);
        return record;
    }

    public List<ServiceRecord> listServiceRecords() {
        return List.copyOf(records);
    }

    public List<ServiceRecord> listRecordsForVehicle(String vehicleId) {
        List<ServiceRecord> filtered = new ArrayList<>();
        for (ServiceRecord record : records) {
            if (record.getVehicleId().equals(vehicleId)) {
                filtered.add(record);
            }
        }
        return filtered;
    }

    public Booking completeBooking(String bookingId, double cost) {
        Booking booking = findBookingById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalArgumentException("Booking is already completed.");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        addServiceRecord(booking.getVehicleId(), booking.getRequestedDate(), booking.getDescription(), cost);
        return booking;
    }

    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
