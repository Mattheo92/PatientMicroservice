package com.Mattheo92.PatientMicroservice.service;

import com.Mattheo92.PatientMicroservice.client.PatientClient;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientClient patientClient;

    public List<VisitDto> getVisitsForPatient(Long patientId) {
        return patientClient.getVisitsForPatient(patientId);
    }

    public void registerPatient(Long visitId, Long patientId) {
        patientClient.registerPatient(visitId, patientId);
    }

    public List<VisitDto> getVisitsForDoctor(Long doctorId) {
        return patientClient.getVisitsForDoctor(doctorId);
    }

    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(String specialization, LocalDate date) {
        return patientClient.getAvailableVisitsBySpecializationAndDate(specialization, date);
    }
}