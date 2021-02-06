package com.company.storeapi.repositories.species.facade;

import com.company.storeapi.model.entity.Species;

import java.util.List;

public interface SpeciesRepositoryFacade {

    List<Species> getAll() ;

    Species validateAndGetById(String id);

    Species save(Species species) ;

    void delete(String  id) ;

    boolean existsSpeciesByDescription(String description);

}
