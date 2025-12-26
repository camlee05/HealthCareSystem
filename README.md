🏥 Hospital Microservices System

A Hospital Management System built using Microservices Architecture, designed to manage patients, doctors, treatment rooms, appointments, and other hospital-related operations.

This project is developed for learning and research purposes, while simulating a real-world scalable system with independently deployable services.

📌 Project Goals

Apply Microservices Architecture to hospital management

Clearly separate business logic across services

Enable easy scalability, maintenance, and independent deployment

Gain hands-on experience with Spring Boot, RESTful APIs, and Docker

👥 Target Users
🔹 Administrator (Admin)

System configuration

Data and service management

🔹 Doctors

View patient lists

Manage appointment schedules

🔹 Medical Staff

Manage patient records

Manage treatment rooms

🔹 Patients (future extension)

View personal information

View appointment schedules

🧱 System Architecture

The system is composed of independent microservices communicating via RESTful APIs.

Core Microservices

patient-service – Patient records management

doctor-service – Doctor information management

room-service – Treatment room management

appointment-service (optional) – Appointment scheduling

api-gateway (optional) – Centralized API gateway

discovery-server (Eureka) (optional) – Service discovery

🛠️ Technology Stack
Layer	Technology
Backend	Java, Spring Boot
API Communication	RESTful API
Database	MySQL / PostgreSQL
ORM	JPA / Hibernate
Build Tool	Maven
Containerization	Docker, Docker Compose
Service Discovery	Eureka (optional)
🚀 Getting Started
1️⃣ Clone the repository
git clone https://github.com/your-username/hospital-microservices.git
cd hospital-microservices

2️⃣ Run a service locally
cd patient-service
mvn spring-boot:run

3️⃣ Run all services with Docker
docker compose up -d --build

📚 Academic Purpose

Course project for Distributed Systems / Software Architecture

Practice and research on Microservices Architecture

Improve teamwork and system design skills
