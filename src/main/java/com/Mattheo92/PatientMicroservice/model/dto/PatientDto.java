package com.Mattheo92.PatientMicroservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
    public class PatientDto {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("email")
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("birthday")
        private LocalDate birthday;

        @Override
        public String toString() {
            return "PatientDto{" +
                    "id : " + id +
                    ", email : '" + email + '\'' +
                    ", first name : '" + firstName + '\'' +
                    ", last name : '" + lastName + '\'' +
                    ", birthday : " + birthday +
                    '}';
        }
    }

