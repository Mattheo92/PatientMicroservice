package com.Mattheo92.PatientMicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleDoctorDto {
    private Long id;
    private String name;
    private String surname;
    private String specialization;
}
