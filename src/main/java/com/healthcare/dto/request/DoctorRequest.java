package com.healthcare.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String specialization;

	@NotBlank
	private String phone;

	@NotBlank
	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private Integer experience;
}
