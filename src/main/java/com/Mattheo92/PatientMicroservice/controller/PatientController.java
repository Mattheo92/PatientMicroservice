package com.Mattheo92.PatientMicroservice.controller;

import com.Mattheo92.PatientMicroservice.model.Visit;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import com.Mattheo92.PatientMicroservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get visits by patient id", description = "Returned list of visits for patient with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned visits for patient"),
            @ApiResponse(responseCode = "404", description = "Patient not found")})
    @GetMapping("/patient/{patientId}")
    public List<VisitDto> getVisitsByPatientId(@PathVariable("patientId") Long patientId) {
        return patientService.getVisitsByPatientId(patientId);
    }

    @Operation(summary = "Register patient for a visit", description = "Register patient for a available visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully registered patient "),
            @ApiResponse(responseCode = "400", description = "Patient not found or visit not found")})
    @PostMapping("/{visitId}/patients/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerPatientForVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        patientService.registerPatientForVisit(visitId, patientId);
    }

    @Operation(summary = "Get available visits for a doctor", description = "Return available visits for a doctor with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned available visits"),
            @ApiResponse(responseCode = "404", description = "Docotr not found")})
    @GetMapping("/doctor/{doctorId}")
    public List<VisitDto> getVisitsByDoctorId(@PathVariable("doctorId") Long doctorId) {
        return patientService.getVisitsByDoctorId(doctorId);
    }

    @Operation(summary = "Get available dates by specialization", description = "Return available visits for a doctor with given specialization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned available visits"),
            @ApiResponse(responseCode = "404", description = "Specialization not found")})
    @GetMapping("/doctors/{specialization}/date/{date}")
    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(
            @PathVariable("specialization") String specialization,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return patientService.getAvailableVisitsBySpecializationAndDate(specialization, date);
    }
}
