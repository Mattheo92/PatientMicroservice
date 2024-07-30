package com.Mattheo92.PatientMicroservice.service;

import com.Mattheo92.PatientMicroservice.client.PatientClient;
import com.Mattheo92.PatientMicroservice.handler.exception.PatientNotFoundException;
import com.Mattheo92.PatientMicroservice.handler.exception.VisitNotFoundException;
import com.Mattheo92.PatientMicroservice.model.Patient;
import com.Mattheo92.PatientMicroservice.model.Visit;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientClient patientClient;

    private PatientService patientService;

    @BeforeEach
    public void setup() {
        this.patientService = new PatientService(patientClient);
    }

    @Test
    public void getVisitsByPatientId_PositiveCase() {
        Long patientId = 1L;

        Visit visit1 = new Visit();
        visit1.setId(1L);
        visit1.setStartDate(LocalDateTime.of(2025, 7, 1, 10, 0));
        visit1.setEndDate(LocalDateTime.of(2025, 7, 1, 11, 0));

        Visit visit2 = new Visit();
        visit2.setId(2L);
        visit2.setStartDate(LocalDateTime.of(2025, 7, 2, 14, 0));
        visit2.setEndDate(LocalDateTime.of(2025, 7, 2, 15, 0));

        List<Visit> visits = List.of(visit1, visit2);

        VisitDto visitDto1 = new VisitDto(visit1.getStartDate(), visit1.getEndDate());
        VisitDto visitDto2 = new VisitDto(visit2.getStartDate(), visit2.getEndDate());
        List<VisitDto> expectedVisitDtoList = List.of(visitDto1, visitDto2);

        when(patientClient.getVisitsForPatient(patientId)).thenReturn(expectedVisitDtoList);

        List<VisitDto> result = patientService.getVisitsForPatient(patientId);

        assertEquals(expectedVisitDtoList.size(), result.size());
        assertEquals(expectedVisitDtoList.get(0).getStartDate(), result.get(0).getStartDate());
        assertEquals(expectedVisitDtoList.get(1).getStartDate(), result.get(1).getStartDate());
    }

    @Test
    void getVisitsByPatientId_PatientNotExists_ReturnedException() {
        Long id = 1L;
        when(patientClient.getVisitsForPatient(id)).thenThrow(FeignException.NotFound.class);

        assertThrows(FeignException.NotFound.class, () -> {
            patientService.getVisitsForPatient(id);
        });
    }

    @Test
    public void getAvailableVisitsByDoctorId_PositiveCase() {
        Long doctorId = 1L;

        VisitDto visitDto1 = new VisitDto(LocalDateTime.of(2025, 7, 1, 10, 0), LocalDateTime.of(2025, 7, 1, 11, 0));
        VisitDto visitDto2 = new VisitDto(LocalDateTime.of(2025, 7, 2, 14, 0), LocalDateTime.of(2025, 7, 2, 15, 0));
        List<VisitDto> availableVisits = List.of(visitDto1, visitDto2);

        when(patientClient.getVisitsForDoctor(doctorId)).thenReturn(availableVisits);

        List<VisitDto> result = patientService.getVisitsForDoctor(doctorId);

        assertEquals(availableVisits.size(), result.size());
        assertEquals(availableVisits.get(0).getStartDate(), result.get(0).getStartDate());
        assertEquals(availableVisits.get(1).getStartDate(), result.get(1).getStartDate());
    }

    @Test
    public void getAvailableVisitsByDoctorId_NegativeCase() {
        Long doctorId = 1L;

        when(patientClient.getVisitsForDoctor(doctorId)).thenReturn(List.of());

        List<VisitDto> result = patientService.getVisitsForDoctor(doctorId);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAvailableVisitsByDoctorSpecializationAndByDate_PositiveCase() {
        String specialization = "Cardiology";
        LocalDate date = LocalDate.of(2025, 7, 1);

        VisitDto visitDto1 = new VisitDto(LocalDateTime.of(2025, 7, 1, 10, 0), LocalDateTime.of(2025, 7, 1, 11, 0));
        VisitDto visitDto2 = new VisitDto(LocalDateTime.of(2025, 7, 1, 14, 0), LocalDateTime.of(2025, 7, 1, 15, 0));
        List<VisitDto> availableVisits = List.of(visitDto1, visitDto2);

        when(patientClient.getAvailableVisitsBySpecializationAndDate(specialization, date)).thenReturn(availableVisits);

        List<VisitDto> result = patientService.getAvailableVisitsBySpecializationAndDate(specialization, date);

        assertEquals(availableVisits.size(), result.size());
        assertEquals(availableVisits.get(0).getStartDate(), result.get(0).getStartDate());
        assertEquals(availableVisits.get(1).getStartDate(), result.get(1).getStartDate());
    }

    @Test
    public void getAvailableVisitsByDoctorSpecializationAndByDate_NegativeCase() {
        String specialization = "Cardiology";
        LocalDate date = LocalDate.of(2025, 7, 1);

        when(patientClient.getAvailableVisitsBySpecializationAndDate(specialization, date)).thenReturn(List.of());

        List<VisitDto> result = patientService.getAvailableVisitsBySpecializationAndDate(specialization, date);

        assertTrue(result.isEmpty());
    }

    @Test
    void registerPatientForVisit_PositiveCase() {
        Long visitId = 1L;
        Long patientId = 1L;

        patientService.registerPatient(visitId, patientId);

        verify(patientClient, times(1)).registerPatient(visitId, patientId);
    }

    @Test
    void registerPatientForVisit_PatientNotExists_ReturnedException() {
        Long visitId = 2L;
        Long patientId = 1L;

        doThrow(FeignException.NotFound.class).when(patientClient).registerPatient(visitId, patientId);

        assertThrows(FeignException.NotFound.class, () -> {
            patientService.registerPatient(visitId, patientId);
        });

        verify(patientClient, times(1)).registerPatient(visitId, patientId);
    }
}
