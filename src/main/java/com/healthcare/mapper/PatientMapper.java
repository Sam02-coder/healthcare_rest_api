package com.healthcare.mapper;

import com.healthcare.dto.response.PatientResponse;
import com.healthcare.entity.Patient;

public class PatientMapper {

	public static PatientResponse mapToResponse(Patient patient) {
		PatientResponse response=new PatientResponse();
		
		response.setId(patient.getId());
		response.setName(patient.getName());
		response.setGender(patient.getGender());
		response.setDob(patient.getDob());
		response.setPhone(patient.getPhone());
		response.setEmail(patient.getEmail());
		response.setAddress(patient.getAddress());
		
		return response;
	}
}
