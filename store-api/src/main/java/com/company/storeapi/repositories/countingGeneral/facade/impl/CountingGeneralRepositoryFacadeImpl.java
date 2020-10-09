package com.company.storeapi.repositories.countingGeneral.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.repositories.countingGeneral.CountingGeneralRepository;
import com.company.storeapi.repositories.countingGeneral.facade.CountingGeneralRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CountingGeneralRepositoryFacadeImpl implements CountingGeneralRepositoryFacade {

    private final CountingGeneralRepository countingGeneralRepository;

    public CountingGeneralRepositoryFacadeImpl(CountingGeneralRepository countingGeneralRepository) {
        this.countingGeneralRepository = countingGeneralRepository;
    }

    @Override
    public List<CountingGeneral> getAllCountingGeneral() {
        try {
            return Optional.of(countingGeneralRepository.findAll())
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros"));
        }catch (EmptyResultDataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public CountingGeneral saveCountingGeneral(CountingGeneral countingGeneral) {
        return countingGeneralRepository.save(countingGeneral);
    }

    @Override
    public CountingGeneral validateCountingGeneral(String id) {
        return countingGeneralRepository.findById(id).orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros"));

    }
}
