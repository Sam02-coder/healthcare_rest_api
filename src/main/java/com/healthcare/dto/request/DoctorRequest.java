package com.healthcare.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequest {
	@Schema(example = "Dr. Rajesh Kumar")
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
