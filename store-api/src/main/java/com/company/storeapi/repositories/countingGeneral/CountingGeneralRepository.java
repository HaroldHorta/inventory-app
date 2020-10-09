package com.company.storeapi.repositories.countingGeneral;

import com.company.storeapi.model.entity.CountingGeneral;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountingGeneralRepository extends MongoRepository<CountingGeneral,String> {
}
