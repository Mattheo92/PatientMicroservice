package com.Mattheo92.PatientMicroservice.service;

import com.Mattheo92.PatientMicroservice.client.PatientClient;
import com.Mattheo92.PatientMicroservice.model.Visit;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PatientService {

    private PatientClient patientClient;

    public List<VisitDto> getVisitsByPatientId(Long patientId) {
        return patientClient.getVisitsByPatientId(patientId);
    }

    public void registerPatientForVisit(Long visitId, Long patientId) {
        patientClient.registerPatientForVisit(visitId, patientId);
    }

    public List<VisitDto> getVisitsByDoctorId(Long doctorId) {
        return patientClient.getVisitsByDoctorId(doctorId);
    }

    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(String specialization, LocalDate date) {
        return patientClient.getAvailableVisitsBySpecializationAndDate(specialization, date);
    }
}
