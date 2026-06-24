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

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Tag(
	    name = "Doctor Management",
	    description = "Doctor CRUD APIs"
	)
public class DoctorController {

	private final DoctorService doctorService;

	@Operation(
		    summary = "Add Doctor",
		    description = "Creates a new doctor."
		)
	@PostMapping
	public ApiResponse<DoctorResponse> addDoctor(
			@Valid @RequestBody DoctorRequest request) {

		return new ApiResponse<>(
				true, 
				"Doctor Added Successfully", 
				doctorService.addDoctor(request));
	}

	@Operation(
		    summary = "Get Doctor By ID",
		    description = "Retrieves doctor details by ID."
		)
	@GetMapping("/{id}")
	public ApiResponse<DoctorResponse> getDoctor(@PathVariable Long id) {

		return new ApiResponse<>(
				true, 
				"Doctor Fetched Successfully", 
				doctorService.getDoctor(id));
	}

	@Operation(
		    summary = "Get All Doctors",
		    description = "Retrieves all doctors with pagination and sorting."
		)
	@GetMapping
	public ApiResponse<PageResponse<DoctorResponse>> getAllDoctors(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		return new ApiResponse<>(
				true, 
				"Doctors Fetched Successfully",
				doctorService.getAllDoctors(page, size, sortBy, sortDir));
	}

	@Operation(
		    summary = "Update Doctor",
		    description = "Updates doctor details."
		)
	@PutMapping("/{id}")
	public ApiResponse<DoctorResponse> updateDoctor(
			@PathVariable Long id, 
			@Valid @RequestBody DoctorRequest request) {

		return new ApiResponse<>(
				true, 
				"Doctor Updated Successfully", 
				doctorService.updateDoctor(id, request));
	}

	@Operation(
		    summary = "Delete Doctor",
		    description = "Deletes a doctor by ID."
		)
	@DeleteMapping("/{id}")
	public ApiResponse<String> deleteDoctor(@PathVariable Long id) {

		doctorService.deleteDoctor(id);

		return new ApiResponse<>(
				true, 
				"Doctor Deleted Successfully", 
				null);
	}

	@Operation(
		    summary = "Search Doctors",
		    description = "Searches doctors using a keyword."
		)
	@GetMapping("/search")
	public ApiResponse<List<DoctorResponse>> searchDoctor(@RequestParam String keyword) {

		return new ApiResponse<>(
				true, 
				"Doctors Found Successfully", 
				doctorService.searchDoctor(keyword));
	}

	@Operation(
		    summary = "Get Doctors By Specialization",
		    description = "Retrieves doctors by specialization."
		)
	@GetMapping("/specialization")
	public ApiResponse<List<DoctorResponse>> getDoctorsBySpecialization(@RequestParam String specialization) {

		return new ApiResponse<>(
				true, 
				"Doctors Found Successfully",
				doctorService.getDoctorsBySpecialization(specialization));
	}
}