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

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;

	@Override
	public DoctorResponse addDoctor(DoctorRequest request) {

		if (doctorRepository.existsByEmail(request.getEmail())) {

			throw new DuplicateResourceException("Email already exists");
		}

		Doctor doctor = new Doctor();

		doctor.setName(request.getName());
		doctor.setEmail(request.getEmail());
		doctor.setPhone(request.getPhone());
		doctor.setSpecialization(request.getSpecialization());
		doctor.setExperience(request.getExperience());

		Doctor savedDoctor = doctorRepository.save(doctor);

		return DoctorMapper.mapToResponse(savedDoctor);
	}

	@Override
	public DoctorResponse getDoctor(Long id) {

		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		return DoctorMapper.mapToResponse(doctor);
	}

	@Override
	public PageResponse<DoctorResponse> getAllDoctors(
			int pageNo,
			int pageSize,
			String sortBy,
			String sortDir) {

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

		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		doctor.setName(request.getName());
		doctor.setEmail(request.getEmail());
		doctor.setPhone(request.getPhone());
		doctor.setSpecialization(request.getSpecialization());
		doctor.setExperience(request.getExperience());

		Doctor updatedDoctor = doctorRepository.save(doctor);

		return DoctorMapper.mapToResponse(updatedDoctor);
	}

	@Override
	public void deleteDoctor(Long id) {

		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		doctorRepository.delete(doctor);
	}
	
	@Override
	public List<DoctorResponse> searchDoctor(
	        String keyword) {

	    return doctorRepository
	            .findByNameContainingIgnoreCase(keyword)
	            .stream()
	            .map(DoctorMapper::mapToResponse)
	            .toList();
	}
	
	@Override
	public List<DoctorResponse> getDoctorsBySpecialization(
	        String specialization) {

	    return doctorRepository
	            .findBySpecializationContainingIgnoreCase(
	                    specialization)
	            .stream()
	            .map(DoctorMapper::mapToResponse)
	            .toList();
	}
}
