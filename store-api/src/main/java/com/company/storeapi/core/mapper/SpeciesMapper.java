package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.Species;
import com.company.storeapi.model.payload.response.species.ResponseSpeciesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SpeciesMapper {


    ResponseSpeciesDTO toVeterinaryDto(Species species);

}
