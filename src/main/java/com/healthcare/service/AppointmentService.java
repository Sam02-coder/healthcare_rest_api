package com.healthcare.service;

import java.util.List;

import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.AppointmentResponse;

public interface AppointmentService {
	
	AppointmentResponse bookAppointment(AppointmentRequest request);
	
	AppointmentResponse getAppointment(Long id);
	
	List<AppointmentResponse> getAllAppointments();
	
	List<AppointmentResponse>  getDoctorAppointments(Long doctorId);
	
	List<AppointmentResponse> getPatientAppointments(Long patientId);
	
	void cancelAppointment(Long id);
}
