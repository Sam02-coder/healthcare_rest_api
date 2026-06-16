package com.healthcare.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.entity.Appointment;
import com.healthcare.entity.Doctor;
import com.healthcare.entity.Patient;
import com.healthcare.enums.AppointmentStatus;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.AppointmentMapper;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository repository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;

	@Override
	public AppointmentResponse bookAppointment(AppointmentRequest request) {
		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));

		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));

		Appointment appointment = new Appointment();

		appointment.setDoctor(doctor);
		appointment.setPatient(patient);

		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setAppointmentTime(request.getAppointmentTime());
		appointment.setStatus(AppointmentStatus.SCHEDULED);

		Appointment save = repository.save(appointment);
		return AppointmentMapper.map(save);
	}

	@Override
	public AppointmentResponse getAppointment(Long id) {
		Appointment appointment = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data Not Found"));
		return AppointmentMapper.map(appointment);
	}

	@Override
	public List<AppointmentResponse> getAllAppointments() {
		List<Appointment> appointments = repository.findAll();

		List<AppointmentResponse> responses = new ArrayList<>();

		for (Appointment appointment : appointments) {
			responses.add(AppointmentMapper.map(appointment));
		}
		return responses;
	}

	@Override
	public List<AppointmentResponse> getDoctorAppointments(Long doctorId) {

		List<Appointment> appointments = repository.findByDoctorId(doctorId);

		List<AppointmentResponse> responses = new ArrayList<>();

		for (Appointment appointment : appointments) {
			responses.add(AppointmentMapper.map(appointment));
		}
		return responses;
	}

	@Override
	public List<AppointmentResponse> getPatientAppointments(Long patientId) {
		List<Appointment> appointments = repository.findByPatientId(patientId);

		List<AppointmentResponse> responses = new ArrayList<>();

		for (Appointment appointment : appointments) {
			responses.add(AppointmentMapper.map(appointment));
		}

		return responses;
	}

	@Override
	public void cancelAppointment(Long id) {
		Appointment appointment=repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Application Not found"));
		
		appointment.setStatus(AppointmentStatus.CANCELLD);
		repository.save(appointment);
	}

}
