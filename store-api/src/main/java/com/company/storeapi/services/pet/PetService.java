package com.company.storeapi.services.pet;


import com.company.storeapi.model.payload.request.pet.RequestAddPetDTO;
import com.company.storeapi.model.payload.request.pet.RequestUpdatePetDTO;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;

import java.util.List;

public interface PetService {

    List<ResponsePetDTO> getAllPet() ;

    ResponsePetDTO validateAndGetPetById(String id);

    ResponsePetDTO savePet(RequestAddPetDTO entity) ;

    ResponsePetDTO updatePet(RequestUpdatePetDTO requestUpdatePetDTO) ;

    void deletePet(String  id) ;

}
