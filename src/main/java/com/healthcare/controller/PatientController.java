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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(
	    name = "Patient Management",
	    description = "Patient CRUD APIs"
	)
public class PatientController {

	private final PatientService service;

	@Operation(
		    summary = "Add Patient",
		    description = "Creates a new patient."
		)
	@PostMapping
	public ApiResponse<PatientResponse> addPatient(@Valid @RequestBody PatientRequest request) {

		return new ApiResponse<>(
				true, 
				"Patient Added Successfully", 
				service.addPatient(request));
	}

	@Operation(
		    summary = "Get Patient By ID",
		    description = "Retrieves patient details by ID."
		)
	@GetMapping("/{id}")
	public ApiResponse<PatientResponse> getPatient(@PathVariable Long id) {

		return new ApiResponse<>(
				true, 
				"Patient Fetched Successfully", 
				service.getPatient(id));
	}

	@Operation(
		    summary = "Get All Patients",
		    description = "Retrieves all patients with pagination and sorting."
		)
	@GetMapping
	public ApiResponse<PageResponse<PatientResponse>> getAllPatients(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		return new ApiResponse<>(true, "Patients Fetched Successfully",
				service.getAllPatients(page, size, sortBy, sortDir));
	}

	@Operation(
		    summary = "Update Patient",
		    description = "Updates patient details."
		)
	@PutMapping("/{id}")
	public ApiResponse<PatientResponse> updatePatient(@PathVariable Long id,
			@Valid @RequestBody PatientRequest request) {

		return new ApiResponse<>(
				true, 
				"Patient Updated Successfully", 
				service.updatePatient(id, request));
	}

	@Operation(
		    summary = "Delete Patient",
		    description = "Deletes a patient by ID."
		)
	@DeleteMapping("/{id}")
	public ApiResponse<String> deletePatient(@PathVariable Long id) {

		service.deletePatient(id);

		return new ApiResponse<>(
				true, 
				"Patient Deleted Successfully", 
				null);
	}

	@Operation(
		    summary = "Search Patients",
		    description = "Searches patients using a keyword."
		)
	@GetMapping("/search")
	public ApiResponse<List<PatientResponse>> searchPatient(@RequestParam String keyword) {

		return new ApiResponse<>(
				true, 
				"Patients Found Successfully", 
				service.searchPatient(keyword));
	}
}