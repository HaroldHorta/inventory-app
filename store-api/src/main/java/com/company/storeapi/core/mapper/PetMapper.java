package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.Pet;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PetMapper {

    ResponsePetDTO toPetDto(Pet pet);

}
