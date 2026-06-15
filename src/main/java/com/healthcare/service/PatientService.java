package com.healthcare.service;

import java.util.List;

import com.healthcare.dto.request.PatientRequest;
import com.healthcare.dto.response.PatientResponse;

public interface PatientService {

	PatientResponse addPatient(PatientRequest request);
	
	PatientResponse getPatient(Long id);
	
	List<PatientResponse> getAllPatient();
	
	PatientResponse updatePatient(Long id, PatientRequest request);
	
	void deletePatient(Long id);
}
