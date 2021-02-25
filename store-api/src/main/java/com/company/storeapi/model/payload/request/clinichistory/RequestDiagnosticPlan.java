package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestDiagnosticPlan {

    private Set<ResponseDiagnosticPlan> diagnosticPlans = new LinkedHashSet<>();

}
