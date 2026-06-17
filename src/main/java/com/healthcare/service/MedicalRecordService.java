package com.healthcare.service;

import java.util.List;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.MedicalRecordResponse;

public interface MedicalRecordService {

	MedicalRecordResponse createReport(MedicalRecordRequest request);
	
	MedicalRecordResponse updateReport(Long id, MedicalRecordRequest request);
	
	List<MedicalRecordResponse> getPatientRecords(Long patientId);
}
