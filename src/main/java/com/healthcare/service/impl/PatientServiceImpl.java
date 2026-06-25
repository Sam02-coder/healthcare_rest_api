package com.healthcare.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.healthcare.dto.request.PatientRequest;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.dto.response.PatientResponse;
import com.healthcare.entity.Patient;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.PatientMapper;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.PatientService;
import com.healthcare.util.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
	private final PatientRepository repository;

	@Override
	public PatientResponse addPatient(PatientRequest request) {
		
		log.info("Adding new patient with email: {}", request.getEmail());
		
		if (repository.existsByEmail(request.getEmail())) {
			
		log.warn("Patient registration failed. Email already exists: {}", request.getEmail());
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
		
		log.info("Patient created succesfully with ID: {}", savePatient.getId());
		
		return PatientMapper.mapToResponse(savePatient);
	}

	@Override
	public PatientResponse getPatient(Long id) {
		Patient patient = repository.findById(id)
				.orElseThrow(() ->
				{
					log.warn("Patient not found with ID: {}", id);
					return new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND);
				});

		return PatientMapper.mapToResponse(patient);
	}

	@Override
	public PageResponse<PatientResponse> getAllPatients(
			int pageNo, 
			int pageSize, 
			String sortBy, 
			String sortDir) {
		
		log.info("Fetching patients. Page: {}, Size: {}, SortBy: {}, Direction: {}", 
				pageNo, pageSize, sortBy, sortDir);

		Sort sort = sortDir.equalsIgnoreCase("asc") 
				? Sort.by(sortBy).ascending() 
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest
				.of(pageNo, pageSize, sort);

		Page<Patient> page = repository.findAll(pageable);

		List<PatientResponse> responses = page
				.getContent()
				.stream()
				.map(PatientMapper::mapToResponse)
				.toList();

		PageResponse<PatientResponse> response = new PageResponse<>();

		response.setContent(responses);
		response.setPageNo(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLast(page.isLast());

		return response;
	}

	@Override
	public PatientResponse updatePatient(Long id, PatientRequest request) {
		
		log.info("Updating patient with ID: {}", id);
		
		Patient patient = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));
		patient.setName(request.getName());
		patient.setGender(request.getGender());
		patient.setDob(request.getDob());
		patient.setPhone(request.getPhone());
		patient.setEmail(request.getEmail());
		patient.setAddress(request.getAddress());

		Patient updatePatient = repository.save(patient);
		
		log.info("Patient updated successfully with ID: {}", updatePatient.getId());
		
		return PatientMapper.mapToResponse(updatePatient);
	}

	@Override
	public void deletePatient(Long id) {
		Patient patient = repository.findById(id)
				.orElseThrow(() ->{
					log.info("Deleting patient with ID: {}", id);
					return new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND);
				});
		repository.delete(patient);
		
		log.info("Patient deleted successfully with ID: {}", id);
	}

	@Override
	public List<PatientResponse> searchPatient(String keyword) {

		return repository
				.findByNameContainingIgnoreCase(keyword)
				.stream()
				.map(PatientMapper::mapToResponse)
				.toList();
	}

}
