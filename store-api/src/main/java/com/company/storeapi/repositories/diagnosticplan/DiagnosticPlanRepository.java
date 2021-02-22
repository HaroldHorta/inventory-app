package com.company.storeapi.repositories.diagnosticplan;

import com.company.storeapi.model.entity.DiagnosticPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiagnosticPlanRepository extends MongoRepository<DiagnosticPlan, String> {

    Boolean existsDiagnosticPlanById(String id);

    Boolean existsDiagnosticPlanByDescription(String description);


}
