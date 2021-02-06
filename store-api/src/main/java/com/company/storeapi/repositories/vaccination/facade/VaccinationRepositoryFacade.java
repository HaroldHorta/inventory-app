package com.company.storeapi.repositories.vaccination.facade;


import com.company.storeapi.model.entity.Vaccination;

import java.util.List;

public interface VaccinationRepositoryFacade {

    List<Vaccination> getAll() ;

    Vaccination validateAndGetById(String id);

    Vaccination save(Vaccination vaccination) ;

    void delete(String  id) ;

    boolean existsVaccinationByDescription(String description);
}
