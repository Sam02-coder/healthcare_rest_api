package com.healthcare.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.healthcare.dto.request.AppointmentRequest;
import com.healthcare.dto.response.AppointmentResponse;
import com.healthcare.entity.Appointment;
import com.healthcare.entity.Doctor;
import com.healthcare.entity.Patient;
import com.healthcare.enums.AppointmentStatus;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.impl.AppointmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void shouldBookAppointmentSuccessfully() {

        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setAppointmentDate(LocalDate.now().plusDays(1));
        request.setAppointmentTime(LocalTime.of(10, 0));

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr Kumar");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Ramesh");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctor));

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(repository.save(any(Appointment.class)))
                .thenReturn(appointment);

        AppointmentResponse response =
                appointmentService.bookAppointment(request);

        assertNotNull(response);
        assertEquals(1L, response.getDoctorId());
        assertEquals(1L, response.getPatientId());
        assertEquals("SCHEDULED", response.getStatus());

        verify(repository, times(1))
                .save(any(Appointment.class));
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFound() {

        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.bookAppointment(request));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {

        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);

        Doctor doctor = new Doctor();
        doctor.setId(1L);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctor));

        when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.bookAppointment(request));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldGetAppointmentSuccessfully() {

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr Kumar");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Ramesh");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setAppointmentTime(LocalTime.of(10, 0));
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(repository.findById(1L))
                .thenReturn(Optional.of(appointment));

        AppointmentResponse response =
                appointmentService.getAppointment(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Dr Kumar", response.getDoctorName());
        assertEquals("Ramesh", response.getPatientName());
    }

    @Test
    void shouldCancelAppointmentSuccessfully() {

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(repository.findById(1L))
                .thenReturn(Optional.of(appointment));

        appointmentService.cancelAppointment(1L);

        assertEquals(
                AppointmentStatus.CANCELLED,
                appointment.getStatus());

        verify(repository, times(1)).save(appointment);
    }

}