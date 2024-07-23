package com.Mattheo92.PatientMicroservice.client;

import com.Mattheo92.PatientMicroservice.fallback.PatientClientFallback;
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

    @GetMapping("/patients/{email}/visits")
    List<VisitDto> getVisitsByPatientEmail(@PathVariable("email") String email);

    @PostMapping("/visits/{visitId}/register")
    void registerPatientForVisit(@PathVariable("visitId") Long visitId, @RequestParam("email") String email);

    @GetMapping("/doctors/{doctorId}/visits")
    List<VisitDto> getVisitsByDoctorId(
            @PathVariable("doctorId") Long doctorId);


    @GetMapping("/doctors/specialization/{specialization}/available-dates")
    List<VisitDto> getAvailableVisitsByDoctorSpecializationAndByDate(
            @PathVariable("specialization") String specialization,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}