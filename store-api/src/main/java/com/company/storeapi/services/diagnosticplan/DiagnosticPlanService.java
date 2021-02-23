package com.company.storeapi.services.diagnosticplan;


import com.company.storeapi.model.payload.request.diagnosticplan.RequestDiagnosticPlan;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;

import java.util.List;

public interface DiagnosticPlanService {

    List<ResponseDiagnosticPlan> getAll();

    ResponseDiagnosticPlan validateAndGetById(String id);

    ResponseDiagnosticPlan save(RequestDiagnosticPlan requestDiagnosticPlan);

    void delete(String id);
}
