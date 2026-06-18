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
import com.healthcare.dto.response.ApiResponse;
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
    public ApiResponse<PatientResponse> addPatient(
            @Valid @RequestBody PatientRequest request) {

        return new ApiResponse<>(
                true,
                "Patient Added Successfully",
                service.addPatient(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PatientResponse> getPatient(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Patient Fetched Successfully",
                service.getPatient(id));
    }

    @GetMapping
    public ApiResponse<List<PatientResponse>> getAllPatients() {

        return new ApiResponse<>(
                true,
                "Patients Fetched Successfully",
                service.getAllPatient());
    }

    @PutMapping("/{id}")
    public ApiResponse<PatientResponse> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {

        return new ApiResponse<>(
                true,
                "Patient Updated Successfully",
                service.updatePatient(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePatient(
            @PathVariable Long id) {

        service.deletePatient(id);

        return new ApiResponse<>(
                true,
                "Patient Deleted Successfully",
                null);
    }
}