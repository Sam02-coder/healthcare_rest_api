package com.healthcare.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentRequest {
	
	@NotNull
	private Long patientId;
	
	@NotNull
	private Long doctorId;
	
	@Future
	private LocalDate appointmentDate;
	
	@NotNull
	private LocalTime appointmentTime;
}
