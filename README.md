# Vehicle Service Management System

A Spring Boot web application for managing vehicles, service records, bookings, and user registration.

## Prerequisites

- JDK 17+
- Maven
- MongoDB Atlas cluster (or local MongoDB for development)

## MongoDB Atlas setup

1. Sign in at [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) and create a free cluster.
2. **Database Access**: create a database user with a password.
3. **Network Access**: add your IP address (or `0.0.0.0/0` for development only).
4. **Connect** → **Drivers** → copy the connection string.
5. Replace `<password>` with your user password and set the database name to `vehicle-service` in the URI path.

Example:

```
mongodb+srv://myuser:myPassword@cluster0.xxxxx.mongodb.net/vehicle-service?retryWrites=true&w=majority
```

## Configure the connection

Set the `MONGODB_URI` environment variable before starting the app.

**Windows (PowerShell):**

```powershell
$env:MONGODB_URI="mongodb+srv://USER:PASSWORD@CLUSTER.mongodb.net/vehicle-service?retryWrites=true&w=majority"
mvn spring-boot:run
```

**macOS / Linux:**

```bash
export MONGODB_URI="mongodb+srv://USER:PASSWORD@CLUSTER.mongodb.net/vehicle-service?retryWrites=true&w=majority"
mvn spring-boot:run
```

You can also copy `.env.example` to `.env` and load it with your IDE or a tool like `dotenv`. Do not commit `.env` (it is in `.gitignore`).

If `MONGODB_URI` is not set, the app falls back to `mongodb://localhost:27017/vehicle-service`.

## Run the web app

```bash
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

Default admin account (created on first startup if missing):

- Username: `admin`
- Password: `password`

## Data stored in MongoDB

| Collection        | Contents                          |
|-------------------|-----------------------------------|
| `vehicles`        | Registered vehicles               |
| `service_records` | Service history                   |
| `customers`       | Customer profiles                 |
| `bookings`        | Service bookings                  |
| `users`           | Login accounts and registration   |

## Features

- Add vehicles and service records
- User registration and login (stored in MongoDB)
- List vehicles and service records
- Vehicle detail pages with per-vehicle history
