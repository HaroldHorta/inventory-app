package com.company.storeapi.repositories.clinichistory.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.ClinicHistory;
import com.company.storeapi.repositories.clinichistory.ClinicHistoryRepository;
import com.company.storeapi.repositories.clinichistory.facade.ClinicHistoryRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClinicHistoryRepositoryFacadeImpl implements ClinicHistoryRepositoryFacade {


    private final ClinicHistoryRepository clinicalHistoryRepository;

    public ClinicHistoryRepositoryFacadeImpl(ClinicHistoryRepository clinicalHistoryRepository) {
        this.clinicalHistoryRepository = clinicalHistoryRepository;
    }

    @Override
    public List<ClinicHistory> getClinicHistoryByCustomerNroDocument(String nroDocument) {
        try {
            return Optional.of(clinicalHistoryRepository.findClinicHistoryByCustomerNroDocument(nroDocument))
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de historias clínicas"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public ClinicHistory validateAndGetClinicHistoryById(String id) {
        return clinicalHistoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "historia clínica con el id: " + id + " no encontrada"));
    }

    @Override
    public ClinicHistory saveClinicHistory(ClinicHistory clinicHistory) {
        return clinicalHistoryRepository.save(clinicHistory);
    }


}
