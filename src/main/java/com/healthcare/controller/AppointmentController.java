package com.healthcare.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.enums.AppointmentStatus;
import com.healthcare.service.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(
	    name = "Appointment Management",
	    description = "Appointment Booking APIs"
	)
public class AppointmentController {

    private final AppointmentService service;

    @Operation(
    	    summary = "Book Appointment",
    	    description = "Creates a new appointment."
    	)
    @PostMapping
    public ApiResponse<AppointmentResponse> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        return new ApiResponse<>(
                true,
                "Appointment Booked Successfully",
                service.bookAppointment(request));
    }

    @Operation(
    	    summary = "Get Appointment By ID",
    	    description = "Retrieves appointment details by ID."
    	)
    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponse> getAppointment(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Appointment Fetched Successfully",
                service.getAppointment(id));
    }

    @Operation(
    	    summary = "Get All Appointments",
    	    description = "Retrieves all appointments."
    	)
    @GetMapping
    public ApiResponse<PageResponse<AppointmentResponse>>
    getAllAppointments(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "appointmentDate")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return new ApiResponse<>(
                true,
                "Appointments Fetched Successfully",
                service.getAllAppointments(
                        page,
                        size,
                        sortBy,
                        sortDir)
        );
    }

    @Operation(
    	    summary = "Get Appointments By Doctor ID",
    	    description = "Retrieves appointments for a doctor."
    	)
    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<PageResponse<AppointmentResponse>>
    getAppointmentsByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "appointmentDate")
            String sortBy,
            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return new ApiResponse<>(
                true,
                "Appointments Fetched Successfully",
                service.getAppointmentsByDoctor(
                        doctorId,
                        page,
                        size,
                        sortBy,
                        sortDir)
        );
    }

    @Operation(
    	    summary = "Get Appointments By Patient ID",
    	    description = "Retrieves appointments for a patient."
    	)
    @GetMapping("/patient/{patientId}")
    public ApiResponse<PageResponse<AppointmentResponse>>
    getAppointmentsByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "appointmentDate")
            String sortBy,
            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return new ApiResponse<>(
                true,
                "Appointments Fetched Successfully",
                service.getAppointmentsByPatient(
                        patientId,
                        page,
                        size,
                        sortBy,
                        sortDir)
        );
    }

    @Operation(
    	    summary = "Cancel Appointment",
    	    description = "Cancels an existing appointment."
    	)
    @DeleteMapping("/{id}/cancel")
    public ApiResponse<String> cancelAppointment(
            @PathVariable Long id) {

        service.cancelAppointment(id);

        return new ApiResponse<>(
                true,
                "Appointment Cancelled Successfully",
                null);
    }
    
    @Operation(
    	    summary = "Get Appointments By Status",
    	    description = "Retrieves appointments by status."
    	)
    @GetMapping("/status")
    public ApiResponse<PageResponse<AppointmentResponse>>
    getAppointmentsByStatus(
            @RequestParam AppointmentStatus status,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "appointmentDate")
            String sortBy,
            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return new ApiResponse<>(
                true,
                "Appointments Fetched Successfully",
                service.getAppointmentsByStatus(
                        status,
                        page,
                        size,
                        sortBy,
                        sortDir)
        );
    }
}