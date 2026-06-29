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

import com.healthcare.dto.request.DoctorRequest;
import com.healthcare.dto.response.DoctorResponse;
import com.healthcare.entity.Doctor;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.service.impl.DoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void shouldAddDoctorSuccessfully() {

        DoctorRequest request = new DoctorRequest();
        request.setName("Dr Kumar");
        request.setEmail("doctor@gmail.com");
        request.setPhone("9876543210");
        request.setSpecialization("Cardiology");
        request.setExperience(10);

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setExperience(request.getExperience());

        when(doctorRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(doctorRepository.save(any(Doctor.class)))
                .thenReturn(doctor);

        DoctorResponse response = doctorService.addDoctor(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Dr Kumar", response.getName());
        assertEquals("doctor@gmail.com", response.getEmail());
        assertEquals("Cardiology", response.getSpecialization());

        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void shouldThrowDuplicateResourceExceptionWhenEmailExists() {

        DoctorRequest request = new DoctorRequest();
        request.setEmail("doctor@gmail.com");

        when(doctorRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> doctorService.addDoctor(request));

        verify(doctorRepository, never()).save(any());
    }

    @Test
    void shouldGetDoctorByIdSuccessfully() {

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr Kumar");
        doctor.setEmail("doctor@gmail.com");
        doctor.setPhone("9876543210");
        doctor.setSpecialization("Cardiology");
        doctor.setExperience(10);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctor));

        DoctorResponse response = doctorService.getDoctor(1L);

        assertNotNull(response);
        assertEquals("Dr Kumar", response.getName());
        assertEquals("Cardiology", response.getSpecialization());
        assertEquals("doctor@gmail.com", response.getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDoctorNotFound() {

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> doctorService.getDoctor(1L));

        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteDoctorSuccessfully() {

        Doctor doctor = new Doctor();
        doctor.setId(1L);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).delete(doctor);
    }

}
