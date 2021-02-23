package com.company.storeapi.repositories.clinichistory.facade.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.ClinicHistory;
import com.company.storeapi.repositories.clinichistory.ClinicHistoryRepository;
import com.company.storeapi.repositories.clinichistory.facade.ClinicHistoryRepositoryFacade;
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
    public ClinicHistory validateAndGetClinicHistoryById(String id) {
        return clinicalHistoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "historia clínica con el id: " + id + " no encontrada"));
    }

    @Override
    public ClinicHistory saveClinicHistory(ClinicHistory clinicHistory) {
        return clinicalHistoryRepository.save(clinicHistory);
    }

    @Override
    public List<ClinicHistory> findClinicHistoryByCustomer(String nroDocument) {
        return Optional.of(clinicalHistoryRepository.findClinicHistoryByPet_Customer_NroDocument(nroDocument))
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "el usuario con el documento: " + nroDocument + " no tiene historias clinicas"));
    }


}
