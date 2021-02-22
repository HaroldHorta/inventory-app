package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.ClinicHistory;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClinicHistoryMapper {

    ResponseClinicHistoryDTO toClinicHistoryDto(ClinicHistory clinicHistory);
}
