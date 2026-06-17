package com.healthcare.dto.response;

import lombok.Data;

@Data
public class MedicalRecordResponse {

	private Long id;

	private Long patientId;
	private String patientName;

	private Long doctorId;
	private String doctorName;

	private String diagnosis;
	private String treatment;
	private String prescription;
	private String notes;
}
