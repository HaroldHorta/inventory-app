package com.company.storeapi.services.diagnosticplan.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.DiagnosticPlanMapper;
import com.company.storeapi.core.util.Util;
import com.company.storeapi.model.entity.DiagnosticPlan;
import com.company.storeapi.model.payload.request.diagnosticplan.RequestDiagnosticPlan;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import com.company.storeapi.repositories.diagnosticplan.facade.DiagnosticPlanRepositoryFacade;
import com.company.storeapi.services.diagnosticplan.DiagnosticPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosticPlanServiceImpl implements DiagnosticPlanService {

    private final DiagnosticPlanRepositoryFacade diagnosticPlanRepositoryFacade;
    private final DiagnosticPlanMapper diagnosticPlanMapper;

    @Override
    public List<ResponseDiagnosticPlan> getAll() {
        List<DiagnosticPlan> diagnosticPlans = diagnosticPlanRepositoryFacade.getAll();
        return diagnosticPlans.stream().map(diagnosticPlanMapper::toDiagnosticPlanDto).collect(Collectors.toList());
    }

    @Override
    public ResponseDiagnosticPlan validateAndGetById(String id) {
        return diagnosticPlanMapper.toDiagnosticPlanDto(diagnosticPlanRepositoryFacade.validateAndGetById(id));
    }

    @Override
    public ResponseDiagnosticPlan save(RequestDiagnosticPlan requestDiagnosticPlan) {
        String description = Util.toCapitalLetters(requestDiagnosticPlan.getDescription());
        boolean isDescription = diagnosticPlanRepositoryFacade.existsDiagnosticPlanByDescription(description);
        if (isDescription) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la vacuna con el nombre " + description + " ya existe");
        }
        if (requestDiagnosticPlan.getDescription().isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La vacuna no puede estar vacía");
        }

        DiagnosticPlan diagnosticPlan = new DiagnosticPlan();
        diagnosticPlan.setDescription(requestDiagnosticPlan.getDescription());
        diagnosticPlan.setCreateAt(new Date());
        return diagnosticPlanMapper.toDiagnosticPlanDto(diagnosticPlanRepositoryFacade.save(diagnosticPlan));
    }
    

    @Override
    public void delete(String id) {
        diagnosticPlanRepositoryFacade.delete(id);
    }
}
