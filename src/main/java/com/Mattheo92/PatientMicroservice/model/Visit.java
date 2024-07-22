package com.Mattheo92.PatientMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
