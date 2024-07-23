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

    public List<VisitDto> getVisitsByPatientEmail(String email) {
        return patientClient.getVisitsByPatientEmail(email);
    }

    public void registerPatientForVisit(Long visitId, String email) {
        patientClient.registerPatientForVisit(visitId, email);
    }

        public List<VisitDto> getAvailableVisitsByDoctorId(Long doctorId) {
        return patientClient.getVisitsByDoctorId(doctorId);
    }

    public List<VisitDto> getAvailableVisitsByDoctorSpecializationAndByDate(String specialization, LocalDate date) {
        return patientClient.getAvailableVisitsByDoctorSpecializationAndByDate(specialization, date);
    }
}