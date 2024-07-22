package com.Mattheo92.PatientMicroservice.fallback;

import com.Mattheo92.PatientMicroservice.client.PatientClient;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientClientFallback implements PatientClient {

    @Override
    public List<VisitDto> getVisitsByPatientEmail(String email) {
        throw new RuntimeException("No visits found for patient");
    }

    @Override
    public void registerPatientForVisit(Long visitId, String email) {
        throw new RuntimeException("Sorry, but You can't register for this visit");
    }

    @Override
    public List<VisitDto> getVisitsByDoctorId(Long doctorId, boolean available) {
        throw new RuntimeException("No available visits found for doctor");
    }

    @Override
    public List<VisitDto> getAvailableVisitsByDoctorSpecializationAndByDate(String specialization, boolean available) {
        throw new RuntimeException("No available dates found for specialization: " + specialization);
    }
}