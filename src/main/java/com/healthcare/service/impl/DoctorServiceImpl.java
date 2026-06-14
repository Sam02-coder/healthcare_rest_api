package com.healthcare.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.entity.Doctor;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.DoctorMapper;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.service.DoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;
	
	
	@Override
	public DoctorResponse addDoctor(
	        DoctorRequest request) {

	    Doctor doctor = new Doctor();

	    doctor.setName(request.getName());
	    doctor.setEmail(request.getEmail());
	    doctor.setPhone(request.getPhone());
	    doctor.setSpecialization(
	            request.getSpecialization());
	    doctor.setExperience(
	            request.getExperience());

	    Doctor savedDoctor =
	            doctorRepository.save(doctor);

	    return DoctorMapper.mapToResponse(
	            savedDoctor);
	}

	@Override
	public DoctorResponse getDoctor(
	        Long id) {

	    Doctor doctor =
	            doctorRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Doctor not found with id " + id));

	    return DoctorMapper.mapToResponse(
	            doctor);
	}

	@Override
	public List<DoctorResponse> getAllDoctors() {

	    List<Doctor> doctors =
	            doctorRepository.findAll();

	    List<DoctorResponse> responses =
	            new ArrayList<>();

	    for (Doctor doctor : doctors) {

	        responses.add(
	                DoctorMapper.mapToResponse(
	                        doctor));
	    }

	    return responses;
	}

	@Override
	public DoctorResponse updateDoctor(
	        Long id,
	        DoctorRequest request) {

	    Doctor doctor =
	            doctorRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Doctor not found with id " + id));

	    doctor.setName(request.getName());
	    doctor.setEmail(request.getEmail());
	    doctor.setPhone(request.getPhone());
	    doctor.setSpecialization(
	            request.getSpecialization());
	    doctor.setExperience(
	            request.getExperience());

	    Doctor updatedDoctor =
	            doctorRepository.save(doctor);

	    return DoctorMapper.mapToResponse(
	            updatedDoctor);
	}

	@Override
	public void deleteDoctor(Long id) {

	    Doctor doctor =
	            doctorRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Doctor not found with id " + id));

	    doctorRepository.delete(doctor);
	}
}
