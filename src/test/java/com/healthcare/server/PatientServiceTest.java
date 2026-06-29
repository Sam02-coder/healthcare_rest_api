package com.healthcare.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.healthcare.dto.request.PatientRequest;
import com.healthcare.dto.response.PatientResponse;
import com.healthcare.entity.Patient;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.impl.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void shouldAddPatientSuccessfully() {

        PatientRequest request = new PatientRequest();
        request.setName("Ramesh");
        request.setGender("Male");
        request.setDob(LocalDate.of(2000, 1, 1));
        request.setPhone("9876543210");
        request.setEmail("ramesh@gmail.com");
        request.setAddress("Bangalore");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName(request.getName());
        patient.setGender(request.getGender());
        patient.setDob(request.getDob());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());

        when(repository.existsByEmail(request.getEmail())).thenReturn(false);
        when(repository.save(any(Patient.class))).thenReturn(patient);

        PatientResponse response = patientService.addPatient(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Ramesh", response.getName());
        assertEquals("Male", response.getGender());
        assertEquals("ramesh@gmail.com", response.getEmail());

        verify(repository, times(1)).save(any(Patient.class));
    }

    @Test
    void shouldThrowDuplicateResourceExceptionWhenEmailExists() {

        PatientRequest request = new PatientRequest();
        request.setEmail("ramesh@gmail.com");

        when(repository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> patientService.addPatient(request));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldGetPatientSuccessfully() {

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Ramesh");
        patient.setGender("Male");
        patient.setDob(LocalDate.of(2000, 1, 1));
        patient.setPhone("9876543210");
        patient.setEmail("ramesh@gmail.com");
        patient.setAddress("Bangalore");

        when(repository.findById(1L))
                .thenReturn(Optional.of(patient));

        PatientResponse response = patientService.getPatient(1L);

        assertNotNull(response);
        assertEquals("Ramesh", response.getName());
        assertEquals("Male", response.getGender());
        assertEquals("Bangalore", response.getAddress());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenPatientNotFound() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> patientService.getPatient(1L));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldDeletePatientSuccessfully() {

        Patient patient = new Patient();
        patient.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(repository, times(1)).delete(patient);
    }

}