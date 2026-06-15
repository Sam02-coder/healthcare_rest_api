package com.healthcare.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientRequest {

	@NotBlank
	private String name;
	
	@NotBlank
	private String gender;
	
	@NotNull
	private LocalDate dob;
	
	@NotBlank
	private String phone;
	
	@Email
	private String email;
	
	@NotBlank
	private String address;

}
