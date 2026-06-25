package com.healthcare.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.dto.response.PageResponse;
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
import com.healthcare.util.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository repository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;

	@Override
	public AppointmentResponse bookAppointment(AppointmentRequest request) {
		
		log.info("Booking appointment. Patient ID: {}, Doctor ID: {}",
		        request.getPatientId(), request.getDoctorId());
		
		Doctor doctor = doctorRepository.findById(request.getDoctorId())
		        .orElseThrow(() -> {

		            log.warn("Doctor not found with ID: {}", request.getDoctorId());

		            return new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND);
		        });

		Patient patient = patientRepository.findById(request.getPatientId())
		        .orElseThrow(() -> {

		            log.warn("Patient not found with ID: {}", request.getPatientId());

		            return new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND);
		        });

		Appointment appointment = new Appointment();

		appointment.setDoctor(doctor);
		appointment.setPatient(patient);

		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setAppointmentTime(request.getAppointmentTime());
		appointment.setStatus(AppointmentStatus.SCHEDULED);

		Appointment save = repository.save(appointment);
		
		log.info("Appointment booked successfully with ID: {}", save.getId());
		
		return AppointmentMapper.map(save);
	}

	@Override
	public AppointmentResponse getAppointment(Long id) {
		Appointment appointment = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.APPOINTMENT_NOT_FOUND));
		return AppointmentMapper.map(appointment);
	}

	@Override
	public PageResponse<AppointmentResponse> getAllAppointments(
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {
		
		log.info("Fetching appointments. Page: {}, Size: {}, SortBy: {}, Direction: {}",
		        pageNo, pageSize, sortBy, sortDir);

	    Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable =
	            PageRequest.of(pageNo, pageSize, sort);

	    Page<Appointment> page =
	            repository.findAll(pageable);

	    List<AppointmentResponse> responses =
	            page.getContent()
	                    .stream()
	                    .map(AppointmentMapper::map)
	                    .toList();

	    PageResponse<AppointmentResponse> response =
	            new PageResponse<>();

	    response.setContent(responses);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());

	    return response;
	}
	@Override
	public PageResponse<AppointmentResponse> getAppointmentsByPatient(
	        Long patientId,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {

		log.info("Fetching appointments for Patient ID: {}", patientId);
		
	    Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable =
	            PageRequest.of(pageNo, pageSize, sort);

	    Page<Appointment> page =
	            repository.findByPatientId(
	                    patientId,
	                    pageable);

	    List<AppointmentResponse> responses =
	            page.getContent()
	                    .stream()
	                    .map(AppointmentMapper::map)
	                    .toList();

	    PageResponse<AppointmentResponse> response =
	            new PageResponse<>();

	    response.setContent(responses);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());

	    return response;
	}

	@Override
	public PageResponse<AppointmentResponse> getAppointmentsByDoctor(
	        Long doctorId,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {
		
		log.info("Fetching appointments for Doctor ID: {}", doctorId);

	    Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable =PageRequest.of(pageNo, pageSize, sort);

	    Page<Appointment> page =repository.findByDoctorId(
	                    doctorId,
	                    pageable);

	    List<AppointmentResponse> responses =
	            page.getContent()
	                    .stream()
	                    .map(AppointmentMapper::map)
	                    .toList();

	    PageResponse<AppointmentResponse> response =
	            new PageResponse<>();

	    response.setContent(responses);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());

	    return response;
	}

	@Override
	public PageResponse<AppointmentResponse> getAppointmentsByStatus(
	        AppointmentStatus status,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {
		
		log.info("Fetching appointments with status: {}", status);

	    Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable =PageRequest.of(pageNo, pageSize, sort);

	    Page<Appointment> page =repository.findByStatus(
	                    status,
	                    pageable);

	    List<AppointmentResponse> responses =
	            page.getContent()
	                    .stream()
	                    .map(AppointmentMapper::map)
	                    .toList();

	    PageResponse<AppointmentResponse> response =
	            new PageResponse<>();

	    response.setContent(responses);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());

	    return response;
	}

	@Override
	public void cancelAppointment(Long id) {
		Appointment appointment=repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.APPOINTMENT_NOT_FOUND));
		
		appointment.setStatus(AppointmentStatus.CANCELLED);
		repository.save(appointment);
		
		log.info("Appointment cancelled successfully with ID: {}", id);
	}

}
