package com.healthcare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
 
	@GetMapping("/heath")
	public String check() {
		return "Heathcare Management System Running";
	}
}
