# TicketDesk – Campus Movie/Show Ticket Booking System

## 📌 Project Overview

TicketDesk is a Spring Boot based campus movie/show ticket booking system.
It allows administrators to create and manage shows, while students can
book available seats online, cancel bookings, and check the remaining
seat availability.

The system also prevents overbooking by validating seat availability
before confirming a booking.

---

## 🎯 Objective

To develop a backend system that provides online movie/show ticket
booking for students while efficiently managing seat availability and
preventing overbooking.

---

## ✨ Key Features

- Admin can create and manage shows
- Students can book seats
- Students can cancel bookings
- View available seats for a show
- Prevent overbooking
- Release seats when a booking is cancelled
- CRUD operations for major entities
- RESTful APIs
- MySQL database integration
- Business logic implemented in the Service layer

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST API
- Postman
- IntelliJ IDEA

---

## 🏗️ Architecture

The project follows a layered architecture:

Client / Postman
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
JPA / Hibernate
        ↓
MySQL Database

### Controller Layer
Handles HTTP requests and responses.

### Service Layer
Contains business logic such as seat availability checking
and preventing overbooking.

### Repository Layer
Uses Spring Data JPA to perform database operations.

### Entity Layer
Represents the database tables using JPA entities.

---

## 📂 Main Entities

### Show
Stores information about the available shows.

Main details:
- Show ID
- Title
- Time
- Total Seats

### Student
Stores student information.

### Booking
Stores booking details and connects a student with a show.

---

## 🔄 CRUD Operations

The application supports the following CRUD operations:

### Create
Creates a new show or booking.

### Read
Retrieves show, student and booking information.

### Update
Updates existing records.

### Delete
Deletes or cancels records where applicable.

---

## 🎟️ Booking Workflow

1. Student selects a show.
2. Student specifies the number of seats.
3. System checks the available seats.
4. If sufficient seats are available, the booking is confirmed.
5. If sufficient seats are not available, the booking is rejected.
6. When a booking is cancelled, the corresponding seats are released.

---

## 🔐 Business Rules

- Total confirmed booked seats must never exceed the total seats of a show.
- A booking can only be confirmed when sufficient seats are available.
- Cancelled booking seats are released for the same show.
- Invalid requests are rejected with an appropriate error message.

---

## 🧪 API Testing

The REST APIs are tested using Postman.

Testing includes:

- POST requests
- GET requests
- PUT requests
- DELETE requests
- Normal booking scenarios
- Seat availability validation
- Overbooking scenarios
- Booking cancellation

---

## 🚀 How to Run the Project

### 1. Clone the repository

```bash
git clone <your-github-repository-url>
