package com.example.vehicleservice;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceManager {
    private final VehicleRepository vehicleRepository;
    private final ServiceRecordRepository serviceRecordRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    public ServiceManager(VehicleRepository vehicleRepository,
                          ServiceRecordRepository serviceRecordRepository,
                          CustomerRepository customerRepository,
                          BookingRepository bookingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.serviceRecordRepository = serviceRecordRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    public Vehicle addVehicle(String vehicleMake, String model, int year, String ownerName, String photoPath) {
        Vehicle vehicle = new Vehicle(generateId(), vehicleMake, model, year, ownerName, photoPath);
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> findVehicleById(String vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public List<Vehicle> listVehicles() {
        return vehicleRepository.findAll();
    }

    public Customer addCustomer(String name, String email, String phone) {
        Customer customer = new Customer(generateId(), name, email, phone);
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmailIgnoreCase(email);
    }

    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    public Booking addBooking(String customerId, String vehicleId, LocalDate requestedDate, String description) {
        Booking booking = new Booking(generateId(), customerId, vehicleId, requestedDate, description);
        return bookingRepository.save(booking);
    }

    public Optional<Booking> findBookingById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> listBookingsForVehicle(String vehicleId) {
        return bookingRepository.findByVehicleId(vehicleId);
    }

    public List<Booking> listBookingsForCustomer(String customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public ServiceRecord addServiceRecord(String vehicleId, LocalDate serviceDate, String description, double cost) {
        ServiceRecord record = new ServiceRecord(generateId(), vehicleId, serviceDate, description, cost);
        return serviceRecordRepository.save(record);
    }

    public List<ServiceRecord> listServiceRecords() {
        return serviceRecordRepository.findAll();
    }

    public List<ServiceRecord> listRecordsForVehicle(String vehicleId) {
        return serviceRecordRepository.findByVehicleId(vehicleId);
    }

    public Booking completeBooking(String bookingId, double cost) {
        Booking booking = findBookingById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalArgumentException("Booking is already completed.");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
        addServiceRecord(booking.getVehicleId(), booking.getRequestedDate(), booking.getDescription(), cost);
        return booking;
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = findBookingById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalArgumentException("Booking cannot be cancelled.");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public boolean updateVehicle(String vehicleId, String vehicleMake, String model, int year, String ownerName) {
        Optional<Vehicle> vehicleOpt = findVehicleById(vehicleId);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            vehicle.setMake(vehicleMake);
            vehicle.setModel(model);
            vehicle.setYear(year);
            vehicle.setOwnerName(ownerName);
            vehicleRepository.save(vehicle);
            return true;
        }
        return false;
    }

    public boolean updateCustomer(String customerId, String name, String email, String phone) {
        Optional<Customer> customerOpt = findCustomerById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    public boolean updateBooking(String bookingId, LocalDate requestedDate, String description) {
        Optional<Booking> bookingOpt = findBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if (booking.getStatus() == BookingStatus.PENDING) {
                booking.setRequestedDate(requestedDate);
                booking.setDescription(description);
                bookingRepository.save(booking);
                return true;
            }
        }
        return false;
    }

    public boolean deleteVehicle(String vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            return false;
        }
        vehicleRepository.deleteById(vehicleId);
        return true;
    }

    public boolean deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            return false;
        }
        customerRepository.deleteById(customerId);
        return true;
    }

    public boolean deleteBooking(String bookingId) {
        Optional<Booking> bookingOpt = findBookingById(bookingId);
        if (bookingOpt.isPresent() && bookingOpt.get().getStatus() == BookingStatus.PENDING) {
            bookingRepository.deleteById(bookingId);
            return true;
        }
        return false;
    }

    public Optional<Customer> findCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Vehicle> searchVehiclesByMake(String make) {
        return vehicleRepository.findByMakeIgnoreCase(make);
    }

    public List<Vehicle> searchVehiclesByModel(String model) {
        return vehicleRepository.findByModelIgnoreCase(model);
    }

    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Booking> listPendingBookings() {
        return bookingRepository.findByStatus(BookingStatus.PENDING);
    }

    public List<Booking> listCompletedBookings() {
        return bookingRepository.findByStatus(BookingStatus.COMPLETED);
    }

    public List<Booking> listCancelledBookings() {
        return bookingRepository.findByStatus(BookingStatus.CANCELLED);
    }

    public double getTotalRevenue() {
        return serviceRecordRepository.findAll().stream()
                .mapToDouble(ServiceRecord::getCost)
                .sum();
    }

    public int getTotalServices() {
        return (int) serviceRecordRepository.count();
    }

    public double getAverageServiceCost() {
        long count = serviceRecordRepository.count();
        if (count == 0) {
            return 0.0;
        }
        return getTotalRevenue() / count;
    }

    public List<ServiceRecord> getServiceRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return serviceRecordRepository.findByServiceDateBetween(startDate, endDate);
    }

    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
