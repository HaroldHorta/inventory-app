package com.company.storeapi.repositories.diagnosticplan.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.DiagnosticPlan;
import com.company.storeapi.repositories.diagnosticplan.DiagnosticPlanRepository;
import com.company.storeapi.repositories.diagnosticplan.facade.DiagnosticPlanRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DiagnosticPlanRepositoryFacadeImpl implements DiagnosticPlanRepositoryFacade {

    private final DiagnosticPlanRepository diagnosticPlanRepository;

    public DiagnosticPlanRepositoryFacadeImpl(DiagnosticPlanRepository diagnosticPlanRepository) {
        this.diagnosticPlanRepository = diagnosticPlanRepository;
    }

    @Override
    public List<DiagnosticPlan> getAll() {
        try {
            return Optional.of(diagnosticPlanRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de diagnósticos"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public DiagnosticPlan validateAndGetById(String id) {
        return diagnosticPlanRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "Vacuna con el id: " + id + " no encontrada"));
    }

    @Override
    public DiagnosticPlan save(DiagnosticPlan diagnosticPlan) {
        return diagnosticPlanRepository.save(diagnosticPlan);
    }

    @Override
    public void delete(String id) {
        boolean diagnosticPlan = diagnosticPlanRepository.existsDiagnosticPlanById(id);
        if (diagnosticPlan) {
            diagnosticPlanRepository.deleteById(id);
        } else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "La vacuna no existe");
        }
    }

    @Override
    public boolean existsDiagnosticPlanByDescription(String description) {
        return diagnosticPlanRepository.existsDiagnosticPlanByDescription(description);
    }
}
