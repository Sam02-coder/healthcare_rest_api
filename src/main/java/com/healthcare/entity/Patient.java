package com.healthcare.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "patients")
@Data
@EqualsAndHashCode(callSuper = false)
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String gender;

	private LocalDate dob;

	private String phone;

	@Email
	@Column(unique = true)
	private String email;

	private String address;
	
	@OneToMany(mappedBy = "patient",
			cascade = CascadeType.ALL)
	private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "patient",
			cascade = CascadeType.ALL)
	private List<MedicalRecord> records;
}
