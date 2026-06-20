package com.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

	boolean existsByEmail(String email);
	
	  List<Patient> findByNameContainingIgnoreCase(String keyword);

}
