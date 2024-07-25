package com.Mattheo92.PatientMicroservice.fallback;

import com.Mattheo92.PatientMicroservice.client.PatientClient;
import com.Mattheo92.PatientMicroservice.handler.exception.VisitNotFoundException;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class PatientClientFallback implements PatientClient {

    @Override
    public List<VisitDto> getVisitsByPatientId(Long id) {
        throw new VisitNotFoundException("No visits found for patient");
    }

    @Override
    public void registerPatientForVisit(Long visitId, Long patientId) {
        throw new RuntimeException("Sorry, but you can't register for this visit");
    }

    @Override
    public List<VisitDto> getVisitsByDoctorId(Long doctorId) {
        throw new VisitNotFoundException("No available visits found for doctor");
    }

    @Override
    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(String specialization, LocalDate localDate) {
        throw new VisitNotFoundException("No available dates found for specialization: " + specialization);
    }
}
