package com.healthcare.service;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.dto.response.PageResponse;

public interface MedicalRecordService {

	MedicalRecordResponse createReport(MedicalRecordRequest request);
	
	MedicalRecordResponse updateReport(Long id, MedicalRecordRequest request);
	
	PageResponse<MedicalRecordResponse> getMedicalRecordsByPatient(
	        Long patientId,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);
}
