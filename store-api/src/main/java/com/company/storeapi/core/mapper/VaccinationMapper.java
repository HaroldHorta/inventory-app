package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.Vaccination;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccinationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VaccinationMapper {

    ResponseVaccinationDTO toVaccinationDto(Vaccination vaccination);

}
