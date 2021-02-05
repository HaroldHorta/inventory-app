package com.company.storeapi.repositories.breed.facade;

import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BreedRepositoryFacade {

    List<Breed> getAllBreed() ;

    List<Breed> findAllByStatus (Status status, Pageable pageable);

    int countByStatus (Status status);

    Breed validateAndGetBreedById(String id);

    Breed saveBreed(Breed entity) ;

    void deleteBreed(String  id) ;

    Boolean existsBreedByDescription(String description);

}
