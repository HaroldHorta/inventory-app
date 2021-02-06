package com.company.storeapi.repositories.species;

import com.company.storeapi.model.entity.Species;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpeciesRepository extends MongoRepository<Species,String > {

    Boolean existsSpeciesById(String id);

    Boolean existsSpeciesByDescription(String description);

}
