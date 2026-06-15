package com.healthcare.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientResponse {
	private Long id;
	private String name;
	private String gender;
	private LocalDate dob;
	private String phone;
	private String email;
	private String address;
}
