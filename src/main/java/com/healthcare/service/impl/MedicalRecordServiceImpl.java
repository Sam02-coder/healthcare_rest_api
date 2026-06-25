package com.healthcare.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.MedicalRecordResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.entity.Doctor;
import com.healthcare.entity.MedicalRecord;
import com.healthcare.entity.Patient;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.MedicalRecordMapper;
import com.healthcare.repository.DoctorRepository;
import com.healthcare.repository.MedicalRecordRepository;
import com.healthcare.repository.PatientRepository;
import com.healthcare.service.MedicalRecordService;
import com.healthcare.util.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

	private final MedicalRecordRepository repository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;

	@Override
	public MedicalRecordResponse createReport(MedicalRecordRequest request) {
		
		log.info("Creating medical record for Patient ID: {} and Doctor ID: {}",
		        request.getPatientId(), request.getDoctorId());
		
		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> {
					 log.warn("Patient not found with ID: {}", request.getPatientId());
					 return new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND);
				});

		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> {
					 log.warn("Doctor not found with ID: {}", request.getDoctorId());
					 return new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND);
				});

		MedicalRecord record = new MedicalRecord();
		record.setPatient(patient);
		record.setDoctor(doctor);
		record.setDiagnosis(request.getDiagnosis());
		record.setTreatment(request.getTreatment());
		record.setPrescription(request.getPrescription());
		record.setNotes(request.getNotes());

		MedicalRecord savedRecord = repository.save(record);
		
		log.info("Medical record created successfully with ID: {}", savedRecord.getId());

		return MedicalRecordMapper.map(savedRecord);
	}

	@Override
	public MedicalRecordResponse updateReport(Long id, MedicalRecordRequest request) {
		
		log.info("Updating medical record with ID: {}", id);

		MedicalRecord record = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.RECORD_NOT_FOUND));

		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> {
					 log.warn("Patient not found with ID: {}", request.getPatientId());
					 return new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND);
				});

		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> {
					 log.warn("Doctor not found with ID: {}", request.getDoctorId());
					 return new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND);
				});

		record.setPatient(patient);
		record.setDoctor(doctor);
		record.setDiagnosis(request.getDiagnosis());
		record.setTreatment(request.getTreatment());
		record.setPrescription(request.getPrescription());
		record.setNotes(request.getNotes());

		MedicalRecord updatedRecord = repository.save(record);

		log.info("Medical record updated successfully with ID: {}", updatedRecord.getId());
		
		return MedicalRecordMapper.map(updatedRecord);
	}

	@Override
	public PageResponse<MedicalRecordResponse> getMedicalRecordsByPatient(
	        Long patientId,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {
		
		log.info(
			    "Fetching medical records for Patient ID: {}. Page: {}, Size: {}, SortBy: {}, Direction: {}",
			    patientId, pageNo, pageSize, sortBy, sortDir);

	    Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable =PageRequest.of(
	                    pageNo,
	                    pageSize,
	                    sort);

	    Page<MedicalRecord> page =repository.findByPatientId(
	                    patientId,
	                    pageable);

	    List<MedicalRecordResponse> responses =
	            page.getContent()
	                    .stream()
	                    .map(MedicalRecordMapper::map)
	                    .toList();

	    PageResponse<MedicalRecordResponse> response =
	            new PageResponse<>();

	    response.setContent(responses);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());

	    return response;
	}

}
