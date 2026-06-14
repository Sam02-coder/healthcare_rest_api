package com.healthcare.mapper;

import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.entity.Doctor;

public class DoctorMapper {
public static DoctorResponse mapToResponse(Doctor doctor) {
	DoctorResponse response=new DoctorResponse();
	
	response.setId(doctor.getId());
	response.setEmail(doctor.getEmail());
	response.setExperience(doctor.getExperience());
	response.setName(doctor.getName());
	response.setPhone(doctor.getPhone());
	response.setSpecialization(doctor.getSpecialization());
	return response;
}
}
