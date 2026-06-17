package com.healthcare.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "doctors")
@Data
@EqualsAndHashCode(callSuper = false)
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String specialization;

	@NotBlank
	private String phone;

	@NotBlank
	@Column(unique = true)
	private String email;

	private Integer experience;

	@OneToMany(mappedBy = "doctor", 
			cascade = CascadeType.ALL)
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "doctor", 
			cascade = CascadeType.ALL)
	private List<MedicalRecord> records;
}
