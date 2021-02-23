package com.company.storeapi.repositories.clinichistory;

import com.company.storeapi.model.entity.ClinicHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClinicHistoryRepository extends MongoRepository<ClinicHistory, String> {

    List<ClinicHistory> findClinicHistoryByPet_Customer_NroDocument(String nroDocument);
}
