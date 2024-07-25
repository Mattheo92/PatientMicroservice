package com.Mattheo92.PatientMicroservice.integration;

import com.Mattheo92.PatientMicroservice.client.PatientClient;
import com.Mattheo92.PatientMicroservice.model.dto.VisitDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PatientClientIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(8083);
        WireMock.configureFor("localhost", 8083);
        wireMockServer.start();
    }

    @AfterAll
    public static void end(){
        wireMockServer.stop();
    }

    @Test
    public void GetVisitsForPatient_Status200() throws Exception {
        Long patientId = 1L;

        VisitDto visitDto1 = new VisitDto(LocalDateTime.of(2025, 7, 1, 10, 0), LocalDateTime.of(2025, 7, 1, 11, 0));
        VisitDto visitDto2 = new VisitDto(LocalDateTime.of(2025, 7, 2, 14, 0), LocalDateTime.of(2025, 7, 2, 15, 0));
        List<VisitDto> expectedVisitDtoList = List.of(visitDto1, visitDto2);

        wireMockServer.stubFor(get(urlEqualTo("/patient/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedVisitDtoList))));

        List<VisitDto> result = patientClient.getVisitsByPatientId(1L);

        assertEquals(expectedVisitDtoList.size(), result.size());
        assertEquals(expectedVisitDtoList.get(0).getStartDate(), result.get(0).getStartDate());
        assertEquals(expectedVisitDtoList.get(1).getStartDate(), result.get(1).getStartDate());
    }

    @Test
    public void GetVisitsForPatient_Status404() throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/patient/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Patient not found\"}")));

        RetryableException exception = assertThrows(RetryableException.class, () -> {
            patientClient.getVisitsByPatientId(999L);
        });

        assertEquals("404 Not Found: [Patient not found]", exception.getMessage());
    }

    @Test
    public void GetVisitsForPatient_Status503() throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/patient/1"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Service is unavailable\"}")));

        RetryableException exception = assertThrows(RetryableException.class, () -> {
            patientClient.getVisitsByPatientId(1L);
        });

        assertEquals("503 Service Unavailable: [Service is unavailable]", exception.getMessage());
    }
}
