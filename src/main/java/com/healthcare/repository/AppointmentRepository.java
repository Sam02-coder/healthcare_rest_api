package com.healthcare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.entity.Appointment;
import com.healthcare.enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	Page<Appointment> findByStatus(AppointmentStatus status, Pageable pageable);

	Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

	Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
}
