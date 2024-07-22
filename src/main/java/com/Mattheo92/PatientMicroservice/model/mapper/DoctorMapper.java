package com.Mattheo92.PatientMicroservice.model.mapper;

import com.Mattheo92.PatientMicroservice.model.Doctor;
import com.Mattheo92.PatientMicroservice.model.dto.DoctorDto;
import com.Mattheo92.PatientMicroservice.model.dto.SimpleDoctorDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDto toDto(Doctor source);
    List<DoctorDto> toDtos (List<Doctor> doctors);
    SimpleDoctorDto toSimpleDto(Doctor source);
}
