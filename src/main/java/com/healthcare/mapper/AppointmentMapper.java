package com.healthcare.mapper;

import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.entity.Appointment;

public class AppointmentMapper {
	public static AppointmentResponse map(Appointment appointment) {
		AppointmentResponse response = new AppointmentResponse();

		response.setId(appointment.getId());
		
		response.setDoctorId(appointment.getDoctor().getId());
		
		response.setDoctorName(appointment.getDoctor().getName());
		
		response.setPatientId(appointment.getPatient().getId());
		
		response.setPatientName(appointment.getPatient().getName());
		
		response.setAppointmentDate(appointment.getAppointmentDate());
		
		response.setAppointmentTime(appointment.getAppointmentTime());
		
		response.setStatus(appointment.getStatus().name());

		return response;
	}
}
