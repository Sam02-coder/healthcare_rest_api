package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.service.MedicalRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical_records")
public class MedicalRecordController {

	private final MedicalRecordService service;

	@PostMapping
	public MedicalRecordResponse createRecord(
			@Valid @RequestBody MedicalRecordRequest request) {
		return service.createReport(request);
	}

	@PutMapping("/{id}")
	public MedicalRecordResponse updateRecord(
			@PathVariable Long id, 
			@Valid @RequestBody MedicalRecordRequest request) {
		return service.updateReport(id, request);
	}

	@GetMapping("/{patientId}")
	public List<MedicalRecordResponse> getPatientRecords(
			@PathVariable Long patientId) {
		return service.getPatientRecords(patientId);
	}
}
