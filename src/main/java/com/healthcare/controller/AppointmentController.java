package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ApiResponse<AppointmentResponse> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        return new ApiResponse<>(
                true,
                "Appointment Booked Successfully",
                service.bookAppointment(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponse> getAppointment(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Appointment Fetched Successfully",
                service.getAppointment(id));
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {

        return new ApiResponse<>(
                true,
                "Appointments Fetched Successfully",
                service.getAllAppointments());
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<AppointmentResponse>> getDoctorAppointments(
            @PathVariable Long doctorId) {

        return new ApiResponse<>(
                true,
                "Doctor Appointments Fetched Successfully",
                service.getDoctorAppointments(doctorId));
    }

    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<AppointmentResponse>> getPatientAppointments(
            @PathVariable Long patientId) {

        return new ApiResponse<>(
                true,
                "Patient Appointments Fetched Successfully",
                service.getPatientAppointments(patientId));
    }

    @DeleteMapping("/{id}/cancel")
    public ApiResponse<String> cancelAppointment(
            @PathVariable Long id) {

        service.cancelAppointment(id);

        return new ApiResponse<>(
                true,
                "Appointment Cancelled Successfully",
                null);
    }
}