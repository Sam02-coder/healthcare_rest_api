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
				.orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));

		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new ResourceNotFoundException("Doctor Not FOund"));

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
				.orElseThrow(() -> new ResourceNotFoundException("Medical Record Not Found"));

		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));

		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));

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
		.orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));

		List<MedicalRecord> records = repository.findByPatientId(patientId);

		return records
				.stream()
				.map(MedicalRecordMapper::map)
				.toList();
	}

}
