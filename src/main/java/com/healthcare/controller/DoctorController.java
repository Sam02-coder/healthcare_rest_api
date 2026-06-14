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
    public DoctorResponse addDoctor(
            @Valid
            @RequestBody DoctorRequest request) {

        return doctorService.addDoctor(request);
    }

    @GetMapping("/{id}")
    public DoctorResponse getDoctor(
            @PathVariable Long id) {

        return doctorService.getDoctor(id);
    }

    @GetMapping
    public List<DoctorResponse> getAllDoctors() {

        return doctorService.getAllDoctors();
    }

    @PutMapping("/{id}")
    public DoctorResponse updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorRequest request) {

        return doctorService.updateDoctor(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);
    }

}
