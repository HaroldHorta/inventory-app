package com.company.storeapi.repositories.veterinary;

import com.company.storeapi.model.entity.Veterinary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VeterinaryRepository extends MongoRepository<Veterinary, String> {

    Boolean existsVeterinaryById(String id);

    Boolean existsVeterinaryByProfessionalCard (String professionalCard);
}
