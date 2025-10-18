package com.hospital.appointment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hospital.appointment_service")


public class AppointmentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}
}
