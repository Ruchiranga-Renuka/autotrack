package com.example.vehicleservice;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class VehicleServiceApp {
    public static void main(String[] args) {
        ServiceManager manager = new ServiceManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> addVehicle(manager, scanner);
                case "2" -> listVehicles(manager);
                case "3" -> addServiceRecord(manager, scanner);
                case "4" -> listServiceRecords(manager);
                case "5" -> listRecordsForVehicle(manager, scanner);
                case "0" -> {
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("=== Vehicle Service Management ===");
        System.out.println("1. Add vehicle");
        System.out.println("2. List vehicles");
        System.out.println("3. Add service record");
        System.out.println("4. List all service records");
        System.out.println("5. List service records for vehicle");
        System.out.println("0. Exit");
    }

    private static void addVehicle(ServiceManager manager, Scanner scanner) {
        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Enter year: ");
        int year = readInt(scanner, "year");
        System.out.print("Enter owner name: ");
        String ownerName = scanner.nextLine().trim();

        Vehicle vehicle = manager.addVehicle(make, model, year, ownerName);
        System.out.println("Vehicle added: " + vehicle);
    }

    private static void listVehicles(ServiceManager manager) {
        List<Vehicle> vehicles = manager.listVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered yet.");
            return;
        }

        System.out.println("Registered vehicles:");
        vehicles.forEach(System.out::println);
    }

    private static void addServiceRecord(ServiceManager manager, Scanner scanner) {
        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine().trim();
        if (manager.findVehicleById(vehicleId).isEmpty()) {
            System.out.println("Vehicle not found with ID: " + vehicleId);
            return;
        }

        System.out.print("Enter service date (YYYY-MM-DD): ");
        LocalDate serviceDate = readDate(scanner);
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter cost: ");
        double cost = readDouble(scanner, "cost");

        ServiceRecord record = manager.addServiceRecord(vehicleId, serviceDate, description, cost);
        System.out.println("Service record added: " + record);
    }

    private static void listServiceRecords(ServiceManager manager) {
        List<ServiceRecord> records = manager.listServiceRecords();
        if (records.isEmpty()) {
            System.out.println("No service records available.");
            return;
        }

        System.out.println("Service records:");
        records.forEach(System.out::println);
    }

    private static void listRecordsForVehicle(ServiceManager manager, Scanner scanner) {
        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine().trim();
        if (manager.findVehicleById(vehicleId).isEmpty()) {
            System.out.println("Vehicle not found with ID: " + vehicleId);
            return;
        }

        List<ServiceRecord> records = manager.listRecordsForVehicle(vehicleId);
        if (records.isEmpty()) {
            System.out.println("No service records found for this vehicle.");
            return;
        }

        System.out.println("Service records for vehicle " + vehicleId + ":");
        records.forEach(System.out::println);
    }

    private static int readInt(Scanner scanner, String fieldName) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid " + fieldName + ". Please enter a number: ");
            }
        }
    }

    private static double readDouble(Scanner scanner, String fieldName) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid " + fieldName + ". Please enter a numeric value: ");
            }
        }
    }

    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Use YYYY-MM-DD: ");
            }
        }
    }
}
