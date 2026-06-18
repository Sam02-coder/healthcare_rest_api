package com.healthcare.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.dto.request.PatientRequest;
import com.healthcare.dto.response.PatientResponse;
import com.healthcare.entity.Patient;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.PatientMapper;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.PatientService;
import com.healthcare.util.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
	private final PatientRepository repository;

	@Override
	public PatientResponse addPatient(PatientRequest request) {
		if (repository.existsByEmail(request.getEmail())) {

			throw new DuplicateResourceException("Email already exists");
		}

		Patient patient = new Patient();

		patient.setName(request.getName());
		patient.setGender(request.getGender());
		patient.setDob(request.getDob());
		patient.setPhone(request.getPhone());
		patient.setEmail(request.getEmail());
		patient.setAddress(request.getAddress());

		Patient savePatient = repository.save(patient);
		return PatientMapper.mapToResponse(savePatient);
	}

	@Override
	public PatientResponse getPatient(Long id) {
		Patient patient = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

		return PatientMapper.mapToResponse(patient);
	}

	@Override
	public List<PatientResponse> getAllPatient() {
		List<Patient> patients = repository.findAll();

		List<PatientResponse> responses = new ArrayList<>();

		for (Patient patient : patients) {
			responses.add(PatientMapper.mapToResponse(patient));
		}
		return responses;
	}

	@Override
	public PatientResponse updatePatient(Long id, PatientRequest request) {
		Patient patient = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));
		patient.setName(request.getName());
		patient.setGender(request.getGender());
		patient.setDob(request.getDob());
		patient.setPhone(request.getPhone());
		patient.setEmail(request.getEmail());
		patient.setAddress(request.getAddress());

		Patient updatePatient = repository.save(patient);
		return PatientMapper.mapToResponse(updatePatient);
	}

	@Override
	public void deletePatient(Long id) {
		Patient patient = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));
		repository.delete(patient);

	}

}
