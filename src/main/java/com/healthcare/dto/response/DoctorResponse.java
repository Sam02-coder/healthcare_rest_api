package com.healthcare.dto.response;

import lombok.Data;

@Data
public class DoctorResponse {
	private Long id;
	private String name;
	private String specialization;
	private String phone;
	private String email;
	private Integer experience;
}
