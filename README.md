# Healthcare Management System

A secure Healthcare Management System built using Java, Spring Boot, Spring Security, JWT Authentication, MySQL, Docker, and Swagger.

The system allows administrators, doctors, and patients to manage appointments, medical records, and healthcare operations securely.

## Project Overview

This application provides:

- User Authentication and Authorization
- Doctor Management
- Patient Management
- Appointment Scheduling
- Medical Records Management
- JWT Security
- Docker Deployment
- Swagger API Documentation

## Tech Stack

### Backend
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- MySQL 8

### Documentation
- Swagger OpenAPI

### Testing
- JUnit 5
- Mockito

### Deployment
- Docker
- Docker Compose

### Build Tool
- Maven

## Architecture

Client
   |
REST APIs
   |
Spring Boot
   |
Service Layer
   |
Repository Layer
   |
MySQL Database

+------------+
|   Client   |
+------------+
       |
       v
+----------------+
| Spring Boot    |
| REST APIs      |
+----------------+
       |
       v
+----------------+
| Service Layer  |
+----------------+
       |
       v
+----------------+
| Repository     |
+----------------+
       |
       v
+----------------+
| MySQL Database |
+----------------+

## Features

### Authentication
- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization

### Doctor Module
- Add Doctor
- Update Doctor
- Delete Doctor
- Search Doctors

### Patient Module
- Add Patient
- Update Patient
- Delete Patient
- Search Patients

### Appointment Module
- Book Appointment
- Cancel Appointment
- Appointment History

### Medical Records
- Create Medical Record
- Update Medical Record
- View Patient History

### Additional Features
- Pagination
- Sorting
- Search APIs
- Validation
- Global Exception Handling
- Swagger Documentation

## Database Tables

- users
- doctors
- patients
- appointments
- medical_records

Doctor
   |
   | OneToMany
   |
Appointment
   |
   | ManyToOne
   |
Patient

Doctor
   |
MedicalRecord
   |
Patient

## Authentication APIs

POST /api/auth/register

POST /api/auth/login

## Doctor APIs

GET    /api/doctors

GET    /api/doctors/{id}

POST   /api/doctors

PUT    /api/doctors/{id}

DELETE /api/doctors/{id}

## Patient APIs

GET    /api/patients

GET    /api/patients/{id}

POST   /api/patients

PUT    /api/patients/{id}

DELETE /api/patients/{id}

## Appointment APIs

POST /api/appointments

GET /api/appointments

GET /api/appointments/{id}

PUT /api/appointments/{id}/cancel

## Medical Record APIs

POST /api/medical-records

PUT /api/medical-records/{id}

GET /api/medical-records/patient/{id}

## Installation

### Clone Repository

git clone <repository-url>

cd healthcare-management

### Configure Database

Create database:

CREATE DATABASE healthcare_db;

### Run Application

mvn spring-boot:run

## Docker Setup

Build Image

docker build -t healthcare-app .

Run Containers

docker compose up

## Swagger

Access:

http://localhost:8080/swagger-ui/index.html

## Screenshots

### Swagger UI

![Swagger](docs/swagger.png)

### Login API

![Login](docs/login.png)

## Future Enhancements

- Email Notifications
- Appointment Reminders
- File Uploads
- Redis Caching
- AWS Deployment
- CI/CD Pipeline