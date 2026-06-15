package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.PatientRequest;
import com.healthcare.dto.response.PatientResponse;
import com.healthcare.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService service;

	@PostMapping
	public PatientResponse addPatient(@Valid @RequestBody PatientRequest request) {
		return service.addPatient(request);
	}

	@GetMapping("/{id}")
	public PatientResponse getPatient(@PathVariable Long id) {
		return service.getPatient(id);
	}

	@GetMapping
	public List<PatientResponse> getAllPatients() {
		return service.getAllPatient();
	}

	@PutMapping("/{id}")
	public PatientResponse updatePatient(@PathVariable Long id, 
			@Valid @RequestBody PatientRequest request) {
		return service.updatePatient(id, request);
	}
	
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable Long id) {
		service.deletePatient(id);
	}
	
}
