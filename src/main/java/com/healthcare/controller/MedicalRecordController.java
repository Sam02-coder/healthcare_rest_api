package com.healthcare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.service.MedicalRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical-records")
@Tag(
	    name = "Medical Record Management",
	    description = "Medical Record APIs"
	)
public class MedicalRecordController {

    private final MedicalRecordService service;

    @Operation(
    	    summary = "Add Medical Record",
    	    description = "Creates a new medical record."
    	)
    @PostMapping
    public ApiResponse<MedicalRecordResponse> createRecord(
            @Valid @RequestBody MedicalRecordRequest request) {

        return new ApiResponse<>(
                true,
                "Medical Record Created Successfully",
                service.createReport(request));
    }

    @Operation(
    	    summary = "Update Medical Record",
    	    description = "Updates medical record details."
    	)
    @PutMapping("/{id}")
    public ApiResponse<MedicalRecordResponse> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordRequest request) {

        return new ApiResponse<>(
                true,
                "Medical Record Updated Successfully",
                service.updateReport(id, request));
    }

    @Operation(
    	    summary = "Get Medical Records By Patient ID",
    	    description = "Retrieves medical records of a patient."
    	)
    @GetMapping("/patient/{patientId}")
    public ApiResponse<PageResponse<MedicalRecordResponse>>
    getMedicalRecordsByPatient(

            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDir) {

        return new ApiResponse<>(
                true,
                "Medical Records Fetched Successfully",
                service.getMedicalRecordsByPatient(
                        patientId,
                        page,
                        size,
                        sortBy,
                        sortDir)
        );
    }
}