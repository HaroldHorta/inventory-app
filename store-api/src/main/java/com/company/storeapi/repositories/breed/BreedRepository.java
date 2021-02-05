package com.company.storeapi.repositories.breed;

import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BreedRepository extends MongoRepository<Breed,String > {

    Boolean existsBreedByDescription(String description);

    List<Breed> findAllByStatus (Status status, Pageable pageable);

    int countByStatus (Status status);
}
