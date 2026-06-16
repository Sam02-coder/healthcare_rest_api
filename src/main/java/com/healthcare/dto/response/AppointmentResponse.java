package com.healthcare.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AppointmentResponse {

	private Long id;

	private Long doctorId;

	private String doctorName;

	private Long patientId;

	private String patientName;

	private LocalDate appointmentDate;

	private LocalTime appointmentTime;

	private String status;
}
