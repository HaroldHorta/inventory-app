package com.company.storeapi.repositories.pet;

import com.company.storeapi.model.entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PetRepository extends MongoRepository<Pet, String> {

    Boolean existsPetById(String id);
    List<Pet> findPetByCustomerNroDocument (String nroDocument);
}
