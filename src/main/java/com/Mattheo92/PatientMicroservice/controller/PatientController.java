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

    @Operation(summary = "Get visits by patient id", description = "Zwraca listę wizyt dla konkretnego identyfikatora pacjenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pomyślnie zwrócono wizyty"),
            @ApiResponse(responseCode = "404", description = "Pacjent nie znaleziony")})
    @GetMapping("/patient/{patientId}")
    public List<VisitDto> getVisitsByPatientId(@PathVariable("patientId") Long patientId) {
        return patientService.getVisitsByPatientId(patientId);
    }

    @Operation(summary = "Register patient for a visit", description = "Rejestruje pacjenta na konkretną wizytę")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pomyślnie zarejestrowano pacjenta na wizytę"),
            @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wizyty lub pacjenta")})
    @PostMapping("/{visitId}/patients/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerPatientForVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        patientService.registerPatientForVisit(visitId, patientId);
    }

    @Operation(summary = "Get visits for a doctor", description = "Zwraca listę wizyt dla konkretnego lekarza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pomyślnie zwrócono wizyty"),
            @ApiResponse(responseCode = "404", description = "Lekarz nie znaleziony")})
    @GetMapping("/doctor/{doctorId}")
    public List<VisitDto> getVisitsByDoctorId(@PathVariable("doctorId") Long doctorId) {
        return patientService.getVisitsByDoctorId(doctorId);
    }

    @Operation(summary = "Get available dates by specialization", description = "Zwraca listę dostępnych terminów wizyt dla konkretnej specjalizacji")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pomyślnie zwrócono dostępne terminy"),
            @ApiResponse(responseCode = "404", description = "Specjalizacja nie znaleziona")})
    @GetMapping("/doctors/{specialization}/date/{date}")
    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(
            @PathVariable("specialization") String specialization,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return patientService.getAvailableVisitsBySpecializationAndDate(specialization, date);
    }
}
