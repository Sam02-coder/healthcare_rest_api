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
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.service.MedicalRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PostMapping
    public ApiResponse<MedicalRecordResponse> createRecord(
            @Valid @RequestBody MedicalRecordRequest request) {

        return new ApiResponse<>(
                true,
                "Medical Record Created Successfully",
                service.createReport(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalRecordResponse> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordRequest request) {

        return new ApiResponse<>(
                true,
                "Medical Record Updated Successfully",
                service.updateReport(id, request));
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<MedicalRecordResponse>> getPatientRecords(
            @PathVariable Long patientId) {

        return new ApiResponse<>(
                true,
                "Medical Records Fetched Successfully",
                service.getPatientRecords(patientId));
    }
}