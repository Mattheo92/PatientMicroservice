package com.Mattheo92.PatientMicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
