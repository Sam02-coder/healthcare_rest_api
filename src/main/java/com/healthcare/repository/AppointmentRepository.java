package com.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
