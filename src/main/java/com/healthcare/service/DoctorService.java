package com.healthcare.service;

import java.util.List;

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.dto.response.PageResponse;

public interface DoctorService {

	DoctorResponse addDoctor(DoctorRequest request);

	DoctorResponse getDoctor(Long id);

	PageResponse<DoctorResponse> getAllDoctors(
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);

	List<DoctorResponse> searchDoctor(String keyword);

	List<DoctorResponse> getDoctorsBySpecialization(
	        String specialization);
	
	DoctorResponse updateDoctor(Long id, DoctorRequest request);

	void deleteDoctor(Long id);

}
