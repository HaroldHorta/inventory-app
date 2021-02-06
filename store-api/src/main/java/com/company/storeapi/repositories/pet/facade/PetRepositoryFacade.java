package com.company.storeapi.repositories.pet.facade;

import com.company.storeapi.model.entity.Pet;

import java.util.List;

public interface PetRepositoryFacade {

    List<Pet> getAllPet() ;

    Pet validateAndGetPetById(String id);

    Pet savePet(Pet entity) ;

    void deletePet(String  id) ;

    Boolean existsPetById(String id);
    
}
