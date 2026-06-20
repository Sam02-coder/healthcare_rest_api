package com.healthcare.service;

import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.enums.AppointmentStatus;

public interface AppointmentService {
	
	AppointmentResponse bookAppointment(AppointmentRequest request);
	
	AppointmentResponse getAppointment(Long id);
	
	PageResponse<AppointmentResponse> getAllAppointments(
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);

	PageResponse<AppointmentResponse> getAppointmentsByStatus(
	        AppointmentStatus status,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);

	PageResponse<AppointmentResponse> getAppointmentsByDoctor(
	        Long doctorId,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);

	PageResponse<AppointmentResponse> getAppointmentsByPatient(
	        Long patientId,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);
	
	void cancelAppointment(Long id);
	
}
