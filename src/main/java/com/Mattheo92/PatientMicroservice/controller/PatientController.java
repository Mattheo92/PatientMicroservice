package com.Mattheo92.PatientMicroservice.controller;

import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import com.Mattheo92.PatientMicroservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get visits by patient email", description = "Returns a list of visits for a specific patient email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned visits"),
            @ApiResponse(responseCode = "404", description = "Patient not found")})
    @GetMapping("/patients/{email}/visits")
    public List<VisitDto> getVisitsByPatientEmail(@PathVariable("email") String email) {
        return patientService.getVisitsByPatientEmail(email);
    }

    @Operation(summary = "Register patient for a visit", description = "Registers a patient for a specific visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered patient for the visit"),
            @ApiResponse(responseCode = "400", description = "Invalid visit or patient details")})
    @PostMapping("/visits/{visitId}/register")
    public void registerPatientForVisit(@PathVariable("visitId") Long visitId, @RequestParam("email") String email) {
        patientService.registerPatientForVisit(visitId, email);
    }

    @Operation(summary = "Get visits for a doctor", description = "Returns a list of visits for a specific doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned visits"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")})
    @GetMapping("/doctors/{doctorId}/visits")
    public List<VisitDto> getVisitsByDoctorId(
            @PathVariable("doctorId") Long doctorId) {
        return patientService.getAvailableVisitsByDoctorId(doctorId);
    }

    @Operation(summary = "Get available dates by specialization", description = "Returns a list of available visit dates for a specific specialization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned available dates"),
            @ApiResponse(responseCode = "404", description = "Specialization not found")})
    @GetMapping("/doctors/specialization/{specialization}/available-dates")
    public List<VisitDto> getAvailableVisitsByDoctorSpecializationAndByDate(
            @PathVariable("specialization") String specialization,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return patientService.getAvailableVisitsByDoctorSpecializationAndByDate(specialization, date);
    }
}