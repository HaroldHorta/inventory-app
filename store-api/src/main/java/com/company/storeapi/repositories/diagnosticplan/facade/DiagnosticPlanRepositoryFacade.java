package com.company.storeapi.repositories.diagnosticplan.facade;



import com.company.storeapi.model.entity.DiagnosticPlan;

import java.util.List;

public interface DiagnosticPlanRepositoryFacade {

    List<DiagnosticPlan> getAll() ;

    DiagnosticPlan validateAndGetById(String id);

    DiagnosticPlan save(DiagnosticPlan diagnosticPlan) ;

    void delete(String  id) ;

    boolean existsDiagnosticPlanByDescription(String description);
}
