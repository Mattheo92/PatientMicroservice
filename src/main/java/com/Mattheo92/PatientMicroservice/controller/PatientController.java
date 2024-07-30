package com.Mattheo92.PatientMicroservice.controller;

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

    @Operation(summary = "Get visits by patient id", description = "Returns a list of visits for a patient with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visits returned successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")})
    @GetMapping("/patient/{patientId}")
    public List<VisitDto> getVisitsForPatient(@PathVariable("patientId") Long patientId) {
        return patientService.getVisitsForPatient(patientId);
    }

    @Operation(summary = "Register patient for a visit", description = "Registers a patient for a visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient successfully registered for the visit"),
            @ApiResponse(responseCode = "400", description = "Invalid patient or visit ID"),
            @ApiResponse(responseCode = "404", description = "Patient or visit not found")})
    @PostMapping("/{visitId}/patients/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerPatient(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        patientService.registerPatient(visitId, patientId);
    }

    @Operation(summary = "Get visits by doctor id", description = "Returns a list of visits for a doctor with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visits returned successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")})
    @GetMapping("/doctor/{doctorId}")
    public List<VisitDto> getVisitsForDoctor(@PathVariable("doctorId") Long doctorId) {
        return patientService.getVisitsForDoctor(doctorId);
    }

    @Operation(summary = "Get available visits by specialization and date", description = "Returns a list of available visits for a given specialization and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available visits returned successfully"),
            @ApiResponse(responseCode = "404", description = "Specialization not found")})
    @GetMapping("/doctors/{specialization}/date/{date}")
    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(
            @PathVariable("specialization") String specialization,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return patientService.getAvailableVisitsBySpecializationAndDate(specialization, date);
    }
}