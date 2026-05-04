# Berchtesgaden Explorer API 🏔️

A modern RESTful API built with **Spring Boot 3.5** and **Java 21** for exploring hiking trails and points of interest in the Berchtesgaden region.

## 🚀 Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.x
- **Security:** Spring Security + JWT (Stateless)
- **Database:** PostgreSQL (with Docker support)
- **Persistence:** Spring Data JPA / Hibernate
- **API Docs:** Springdoc-OpenAPI (Swagger UI)
- **Tools:** Lombok, Maven

## ✨ Features

- **Location Management:** 
  - Browse locations with pagination and sorting.
  - Search by name or description.
  - Filter by Category (Hiking, Viewpoint, etc.), Difficulty, or Season.
  - Featured locations for application home screens.
- **Security:**
  - Role-based access control (USER/ADMIN).
  - JWT-based authentication.
  - BCrypt password hashing.
- **Infrastructure:**
  - Dockerized PostgreSQL setup.
  - Global error handling and custom exceptions.

## 🛠️ Getting Started

### Prerequisites

- JDK 21+
- Maven 3.x
- Docker & Docker Compose

### Setup

1. **Spin up the database:**
   ```bash
   docker-compose up -d
   ```

2. **Configure environment:**
   The application is configured to connect to `localhost:5433`. You can modify `src/main/resources/application.properties` if needed.

3. **Build & Run:**
   ```bash
   ./mvnw spring-boot:run
   ```

## 📖 API Documentation

Once the application is running, you can access the interactive Swagger documentation at:
- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI Specs:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🔐 Core Endpoints

### Auth
- `POST /api/auth/register` - Create a new account.
- `POST /api/auth/login` - Get a JWT token.

### Locations (Public)
- `GET /api/locations` - List all active locations (paginated).
- `GET /api/locations/{id}` - Get details for a specific spot.
- `GET /api/locations/search?q={query}` - Search spots.
- `GET /api/locations/featured` - List featured locations.

### Admin (Requires ADMIN role)
- `POST /api/locations` - Create a new location.
- `PUT /api/locations/{id}` - Update existing location.
- `DELETE /api/locations/{id}` - Remove a location.

## 🗄️ Database Schema

The `Location` entity includes:
- **Geospatial:** Latitude, Longitude, Altitude.
- **Metrics:** Distance (km), Duration (min).
- **Metadata:** Category, Difficulty Level (Easy/Medium/Hard), Best Season.
- **UI Helpers:** Featured flag, Tip text.

---
*Developed with ❤️ for the Berchtesgaden Explorer project.*
