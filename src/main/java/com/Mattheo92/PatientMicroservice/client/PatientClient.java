package com.Mattheo92.PatientMicroservice.client;

import com.Mattheo92.PatientMicroservice.fallback.PatientClientFallback;
import com.Mattheo92.PatientMicroservice.model.Visit;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "medical-clinic", configuration = FeignClientProperties.FeignClientConfiguration.class, fallback = PatientClientFallback.class)
public interface PatientClient {

    @GetMapping("/visits/patient/{patientId}")
    List<VisitDto> getVisitsByPatientId(@PathVariable("patientId") Long patientId);

    @PostMapping("/visits/{visitId}/patients/{patientId}")
    Visit registerPatientForVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId);

    @GetMapping("visits/doctor/{doctorId}")
    List<VisitDto> getVisitsByDoctorId(
            @PathVariable("doctorId") Long doctorId);

    @GetMapping("/visits/doctors/{specialization}/date/{date}")
    List<VisitDto> getAvailableVisitsBySpecializationAndDate(
            @PathVariable("specialization") String specialization,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

}