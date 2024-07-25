package com.Mattheo92.PatientMicroservice.controller;

import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import com.Mattheo92.PatientMicroservice.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetVisitsByPatientId() throws Exception {
        Long patientId = 1L;
        VisitDto visitDto1 = new VisitDto(LocalDateTime.of(2025, 7, 1, 10, 0), LocalDateTime.of(2025, 7, 1, 11, 0));
        VisitDto visitDto2 = new VisitDto(LocalDateTime.of(2025, 7, 2, 14, 0), LocalDateTime.of(2025, 7, 2, 15, 0));
        List<VisitDto> visitDtoList = List.of(visitDto1, visitDto2);

        when(patientService.getVisitsByPatientId(patientId)).thenReturn(visitDtoList);

        mockMvc.perform(get("/visits/patient/{patientId}", patientId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value("2025-07-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startDate").value("2025-07-02T14:00:00"));
    }

    @Test
    void getVisitsForPatient_PatientNotExists_ReturnedException() throws Exception {
        Long patientId = 1L;

        when(patientService.getVisitsByPatientId(patientId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/patient/{patientId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Patient not found"));
    }

    @Test
    void registerPatientForVisit_PositiveCase() throws Exception {
        Long visitId = 1L;
        Long patientId = 1L;

        mockMvc.perform(post("/visits/{visitId}/patients/{patientId}", visitId, patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(patientService).registerPatientForVisit(visitId, patientId);
    }

    @Test
    void registerPatientForVisit_PatientNotExists_ReturnedException() throws Exception {
        Long visitId = 1L;
        Long patientId = 2L;

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found")).when(patientService).registerPatientForVisit(visitId, patientId);

        mockMvc.perform(MockMvcRequestBuilders.post("/visits/{visitId}/patients/{patientId}", visitId, patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Patient not found"));

        verify(patientService).registerPatientForVisit(visitId, patientId);
    }

    @Test
    void getVisitsByDoctorId_PositiveCase() throws Exception {
        Long doctorId = 1L;
        VisitDto visitDto = new VisitDto();
        List<VisitDto> visits = List.of(visitDto);

        when(patientService.getVisitsByDoctorId(doctorId)).thenReturn(visits);

        mockMvc.perform(get("/visits/doctor/{doctorId}", doctorId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    void getVisitsByDoctorId_AllVisitsOccupied() throws Exception {
        Long doctorId = 1L;

        when(patientService.getVisitsByDoctorId(doctorId)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctor/{doctorId}", doctorId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void getVisitsBySpecializationAndDate_PositiveCase() throws Exception {
        String specialization = "surgeon";
        LocalDate date = LocalDate.of(2025, 11, 11);
        VisitDto visitDto = new VisitDto();
        visitDto.setStartDate(LocalDateTime.of(2025, 11, 11, 10, 10));
        List<VisitDto> visits = List.of(visitDto);

        when(patientService.getAvailableVisitsBySpecializationAndDate(specialization, date)).thenReturn(visits);

        mockMvc.perform(get("/visits/doctors/{specialization}/date/{date}", specialization, date)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void getVisitsBySpecializationAndDate_NotFound() throws Exception {
        String specialization = "anesthesiologist";
        LocalDate date = LocalDate.of(2024, 7, 24);

        when(patientService.getAvailableVisitsBySpecializationAndDate(specialization, date))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctors/{specialization}/date/{date}", specialization, date)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
