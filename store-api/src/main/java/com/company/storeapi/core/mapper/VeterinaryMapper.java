package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.Veterinary;
import com.company.storeapi.model.payload.request.veterinary.RequestUpdateVeterinaryDTO;
import com.company.storeapi.model.payload.response.veterinary.ResponseVeterinaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VeterinaryMapper {

    Veterinary toVeterinary(ResponseVeterinaryDTO responseVeterinaryDTO);

    ResponseVeterinaryDTO toVeterinaryDto(Veterinary veterinary);

    void updateVeterinaryFromDto(RequestUpdateVeterinaryDTO requestUpdateVeterinaryDTO, @MappingTarget Veterinary veterinary);
}
