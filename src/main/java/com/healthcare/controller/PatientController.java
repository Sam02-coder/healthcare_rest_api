package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.PatientRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.PageResponse;
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
	public ApiResponse<PatientResponse> addPatient(@Valid @RequestBody PatientRequest request) {

		return new ApiResponse<>(
				true, 
				"Patient Added Successfully", 
				service.addPatient(request));
	}

	@GetMapping("/{id}")
	public ApiResponse<PatientResponse> getPatient(@PathVariable Long id) {

		return new ApiResponse<>(
				true, 
				"Patient Fetched Successfully", 
				service.getPatient(id));
	}

	@GetMapping
	public ApiResponse<PageResponse<PatientResponse>> getAllPatients(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		return new ApiResponse<>(true, "Patients Fetched Successfully",
				service.getAllPatients(page, size, sortBy, sortDir));
	}

	@PutMapping("/{id}")
	public ApiResponse<PatientResponse> updatePatient(@PathVariable Long id,
			@Valid @RequestBody PatientRequest request) {

		return new ApiResponse<>(
				true, 
				"Patient Updated Successfully", 
				service.updatePatient(id, request));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<String> deletePatient(@PathVariable Long id) {

		service.deletePatient(id);

		return new ApiResponse<>(
				true, 
				"Patient Deleted Successfully", 
				null);
	}

	@GetMapping("/search")
	public ApiResponse<List<PatientResponse>> searchPatient(@RequestParam String keyword) {

		return new ApiResponse<>(
				true, 
				"Patients Found Successfully", 
				service.searchPatient(keyword));
	}
}