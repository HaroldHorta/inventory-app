package com.company.storeapi.repositories.clinicexam;

import com.company.storeapi.model.entity.ClinicExam;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClinicExamRepository extends MongoRepository<ClinicExam, String> {

    ClinicExam findClinicExamById(String id);

    boolean existsClinicExamById(String id);
}
