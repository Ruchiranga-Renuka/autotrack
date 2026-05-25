# Vehicle Service Management System

A basic Java console application for managing vehicles and service records.

## Run

### Option 1: Maven (recommended)

1. Install Maven and JDK 17.
2. From the project root:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.example.vehicleservice.VehicleServiceApp"
```

### Option 2: Direct Java compile

1. Install JDK 17.
2. From the project root:

```bash
javac -d out src/main/java/com/example/vehicleservice/*.java
java -cp out com.example.vehicleservice.VehicleServiceApp
```

## Features

- Add vehicles
- List registered vehicles
- Add service records for vehicles
- List all service records
- List service records by vehicle ID

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/b5cdc008-ff2f-4a4a-860b-5f42e045907d" />




