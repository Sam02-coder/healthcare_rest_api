package com.healthcare.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.entity.Doctor;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.DoctorMapper;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.service.DoctorService;
import com.healthcare.util.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;

	@Override
	public DoctorResponse addDoctor(DoctorRequest request) {
		
		log.info("Adding new doctor with email: {}", request.getEmail());

		if (doctorRepository.existsByEmail(request.getEmail())) {

		    log.warn("Doctor registration failed. Email already exists: {}", request.getEmail());

		    throw new DuplicateResourceException("Email already exists");
		}

		Doctor doctor = new Doctor();

		doctor.setName(request.getName());
		doctor.setEmail(request.getEmail());
		doctor.setPhone(request.getPhone());
		doctor.setSpecialization(request.getSpecialization());
		doctor.setExperience(request.getExperience());

		Doctor savedDoctor = doctorRepository.save(doctor);
		
		log.info("Doctor created successfully with ID: {}", savedDoctor.getId());

		return DoctorMapper.mapToResponse(savedDoctor);
	}

	@Override
	public DoctorResponse getDoctor(Long id) {

		Doctor doctor = doctorRepository.findById(id)
		        .orElseThrow(() -> {

		            log.warn("Doctor not found with ID: {}", id);

		            return new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND);
		        });

		return DoctorMapper.mapToResponse(doctor);
	}

	@Override
	public PageResponse<DoctorResponse> getAllDoctors(
			int pageNo,
			int pageSize,
			String sortBy,
			String sortDir) {
		
		log.info("Fetching doctors. Page: {}, Size: {}, SortBy: {}, Direction: {}",
		        pageNo, pageSize, sortBy, sortDir);

		Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();
		Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		
		Page<Doctor> page=doctorRepository.findAll(pageable);
		
		 List<DoctorResponse> responses =
		            page.getContent()
		                    .stream()
		                    .map(DoctorMapper::mapToResponse)
		                    .toList();

		 PageResponse<DoctorResponse> response =
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
	public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
		
		log.info("Updating doctor with ID: {}", id);

		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		doctor.setName(request.getName());
		doctor.setEmail(request.getEmail());
		doctor.setPhone(request.getPhone());
		doctor.setSpecialization(request.getSpecialization());
		doctor.setExperience(request.getExperience());

		Doctor updatedDoctor = doctorRepository.save(doctor);
		
		log.info("Doctor updated successfully with ID: {}", updatedDoctor.getId());

		return DoctorMapper.mapToResponse(updatedDoctor);
	}

	@Override
	public void deleteDoctor(Long id) {

		log.info("Deleting doctor with ID: {}", id);
		
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		doctorRepository.delete(doctor);
		
		log.info("Doctor deleted succesfully with ID: {}", id);
	}
	
	@Override
	public List<DoctorResponse> searchDoctor(
	        String keyword) {
		
		log.info("Searching doctors with keyword: {}", keyword);

	    return doctorRepository
	            .findByNameContainingIgnoreCase(keyword)
	            .stream()
	            .map(DoctorMapper::mapToResponse)
	            .toList();
	}
	
	@Override
	public List<DoctorResponse> getDoctorsBySpecialization(
	        String specialization) {

		log.info("Fetching doctors with specialization: {}", specialization);
		
	    return doctorRepository
	            .findBySpecializationContainingIgnoreCase(
	                    specialization)
	            .stream()
	            .map(DoctorMapper::mapToResponse)
	            .toList();
	}
}
