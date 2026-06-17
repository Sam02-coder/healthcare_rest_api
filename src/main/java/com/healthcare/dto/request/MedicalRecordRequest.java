package com.healthcare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordRequest {

	@NotNull
	private Long doctorId;

	@NotNull
	private Long patientId;

	@NotBlank
	private String diagnosis;

	@NotBlank
	private String treatment;

	@NotBlank
	private String prescription;

	private String notes;
}
