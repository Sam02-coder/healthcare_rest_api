package com.healthcare.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.dto.request.MedicalRecordRequest;
import com.healthcare.dto.response.MedicalRecordResponse;
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

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

	private final MedicalRecordRepository repository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;

	@Override
	public MedicalRecordResponse createReport(MedicalRecordRequest request) {
		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		MedicalRecord record = new MedicalRecord();
		record.setPatient(patient);
		record.setDoctor(doctor);
		record.setDiagnosis(request.getDiagnosis());
		record.setTreatment(request.getTreatment());
		record.setPrescription(request.getPrescription());
		record.setNotes(request.getNotes());

		MedicalRecord savedRecord = repository.save(record);

		return MedicalRecordMapper.map(savedRecord);
	}

	@Override
	public MedicalRecordResponse updateReport(Long id, MedicalRecordRequest request) {

		MedicalRecord record = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.RECORD_NOT_FOUND));

		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

		record.setPatient(patient);
		record.setDoctor(doctor);
		record.setDiagnosis(request.getDiagnosis());
		record.setTreatment(request.getTreatment());
		record.setPrescription(request.getPrescription());
		record.setNotes(request.getNotes());

		MedicalRecord updatedRecord = repository.save(record);

		return MedicalRecordMapper.map(updatedRecord);
	}

	@Override
	public List<MedicalRecordResponse> getPatientRecords(Long patientId) {
		patientRepository.findById(patientId)
		.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

		List<MedicalRecord> records = repository.findByPatientId(patientId);

		return records
				.stream()
				.map(MedicalRecordMapper::map)
				.toList();
	}

}
