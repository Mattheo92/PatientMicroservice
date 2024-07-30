package com.Mattheo92.PatientMicroservice.client;

import com.Mattheo92.PatientMicroservice.fallback.PatientClientFallback;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import com.Mattheo92.PatientMicroservice.retryer.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "medicalclinic", configuration = FeignClientProperties.FeignClientConfiguration.class, fallback = PatientClientFallback.class)
public interface PatientClient {

    @GetMapping("visits/patient/{patientId}")
    List<VisitDto> getVisitsForPatient(@PathVariable("patientId") Long patientId);

    @PostMapping("/visits//{visitId}/patients/{patientId}")
    void registerPatient(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId);

    @GetMapping("visits/doctor/{doctorId}")
    List<VisitDto> getVisitsForDoctor(@PathVariable("doctorId") Long doctorId);

    @GetMapping("visits/doctors/{specialization}/date/{date}")
    List<VisitDto> getAvailableVisitsBySpecializationAndDate(
            @PathVariable("specialization") String specialization,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}