package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.payload.request.breed.RequestUpdateBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseBreedDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BreedMapper {

    ResponseBreedDTO toBreedDto(Breed breed);

    void updateBreedFromDto(RequestUpdateBreedDTO requestUpdateBreedDTO, @MappingTarget Breed breed);

}
