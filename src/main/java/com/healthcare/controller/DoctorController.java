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

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ApiResponse<DoctorResponse> addDoctor(
            @Valid @RequestBody DoctorRequest request) {

        return new ApiResponse<>(
                true,
                "Doctor Added Successfully",
                doctorService.addDoctor(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<DoctorResponse> getDoctor(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Doctor Fetched Successfully",
                doctorService.getDoctor(id)
        );
    }

    @GetMapping
    public ApiResponse<List<DoctorResponse>> getAllDoctors() {

        return new ApiResponse<>(
                true,
                "Doctors Fetched Successfully",
                doctorService.getAllDoctors()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<DoctorResponse> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequest request) {

        return new ApiResponse<>(
                true,
                "Doctor Updated Successfully",
                doctorService.updateDoctor(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return new ApiResponse<>(
                true,
                "Doctor Deleted Successfully",
                null
        );
    }
}