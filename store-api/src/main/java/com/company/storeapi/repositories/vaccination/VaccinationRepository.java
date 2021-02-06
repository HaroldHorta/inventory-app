package com.company.storeapi.repositories.vaccination;

import com.company.storeapi.model.entity.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VaccinationRepository extends MongoRepository<Vaccination, String> {

    Boolean existsVaccinationById(String id);

    Boolean existsVaccinationByDescription(String description);


}
