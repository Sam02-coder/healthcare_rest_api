package com.healthcare.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.entity.Doctor;
import com.healthcare.entity.MedicalRecord;
import com.healthcare.entity.Patient;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.repository.MedicalRecordRepository;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.impl.MedicalRecordServiceImpl;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository repository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Test
    void shouldCreateMedicalRecordSuccessfully() {

        MedicalRecordRequest request = new MedicalRecordRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setDiagnosis("Fever");
        request.setTreatment("Rest");
        request.setPrescription("Paracetamol");
        request.setNotes("Drink more water");

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr Kumar");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Ramesh");

        MedicalRecord record = new MedicalRecord();
        record.setId(1L);
        record.setDoctor(doctor);
        record.setPatient(patient);
        record.setDiagnosis(request.getDiagnosis());
        record.setTreatment(request.getTreatment());
        record.setPrescription(request.getPrescription());
        record.setNotes(request.getNotes());

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctor));

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(repository.save(any(MedicalRecord.class)))
                .thenReturn(record);

        MedicalRecordResponse response =
                medicalRecordService.createReport(request);

        assertNotNull(response);
        assertEquals(1L, response.getDoctorId());
        assertEquals(1L, response.getPatientId());
        assertEquals("Fever", response.getDiagnosis());

        verify(repository, times(1))
                .save(any(MedicalRecord.class));
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFound() {

        MedicalRecordRequest request = new MedicalRecordRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);

        Patient patient = new Patient();
        patient.setId(1L);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> medicalRecordService.createReport(request));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {

        MedicalRecordRequest request = new MedicalRecordRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> medicalRecordService.createReport(request));

        verify(repository, never()).save(any());
    }

}