package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

	private final AppointmentService service;

	@PostMapping
	public AppointmentResponse bookAppointment(
			@Valid @RequestBody AppointmentRequest request) {
		return service.bookAppointment(request);
	}

	@GetMapping("/{id}")
	public AppointmentResponse getAppointment(@PathVariable Long id) {
		return service.getAppointment(id);
	}

	@GetMapping
	public List<AppointmentResponse> getAllAppointments() {
		return service.getAllAppointments();
	}

	@GetMapping("/doctor/{doctorId}")
	public List<AppointmentResponse> getDoctorAppointments(
			@PathVariable Long doctorId) {
		return service.getDoctorAppointments(doctorId);
	}

	@GetMapping("/patient/{patientId}")
	public List<AppointmentResponse> getPatientAppointments(
			@PathVariable Long patientId) {
		return service.getPatientAppointments(patientId);
	}

	@DeleteMapping("/{id}/cancel")
	public String cancelAppointments(@PathVariable Long id) {
		service.cancelAppointment(id);

		return "Appointment Cancelled";
	}

}
