package com.healthcare.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;
	
	private String type="Bearer";
	
	private String username;
	
	private String role;
}
