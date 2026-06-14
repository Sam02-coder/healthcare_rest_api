package com.healthcare.service;

import java.util.List;

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.DoctorResponse;

public interface DoctorService {
	
	 DoctorResponse addDoctor(DoctorRequest request);

	 DoctorResponse getDoctor(Long id);

	 List<DoctorResponse> getAllDoctors();
	
	 DoctorResponse updateDoctor(Long id, DoctorRequest request);
	
	 void deleteDoctor(Long id);
	
}
