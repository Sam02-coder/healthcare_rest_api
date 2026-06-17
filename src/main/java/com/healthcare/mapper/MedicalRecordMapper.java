package com.healthcare.mapper;

import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.entity.MedicalRecord;


public class MedicalRecordMapper {

	public static MedicalRecordResponse map(MedicalRecord record) {

		MedicalRecordResponse response = new MedicalRecordResponse();

		response.setId(record.getId());

		response.setPatientId(record.getPatient().getId());
		response.setPatientName(record.getPatient().getName());

		response.setDoctorId(record.getDoctor().getId());
		response.setDoctorName(record.getDoctor().getName());

		response.setDiagnosis(record.getDiagnosis());
		response.setTreatment(record.getTreatment());
		response.setPrescription(record.getPrescription());
		response.setNotes(record.getNotes());

		return response;
	}
}
