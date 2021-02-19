package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.ClinicExam;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClinicExamMapper {

    ResponseClinicExam toClinicExamDto(ClinicExam clinicExam);

}
