package com.company.storeapi.repositories.clicexam.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.ClinicExam;
import com.company.storeapi.repositories.clicexam.ClinicExamRepository;
import com.company.storeapi.repositories.clicexam.facade.ClinicExamRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ClinicExamRepositoryFacadeImpl implements ClinicExamRepositoryFacade {

    private final ClinicExamRepository clinicExamRepository;

    public ClinicExamRepositoryFacadeImpl(ClinicExamRepository clinicExamRepository) {
        this.clinicExamRepository = clinicExamRepository;
    }

    @Override
    public List<ClinicExam> getAllClinicExam() {
        try {
            return Optional.of(clinicExamRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de examenes clínicos"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public ClinicExam validateAndGetClinicExamById(String id) {
        return clinicExamRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "examenes clinicos con el id: " + id + " no encontrada"));
    }

    @Override
    public ClinicExam saveClinicExam(ClinicExam clinicExam) {
        return clinicExamRepository.save(clinicExam);

    }

    @Override
    public void deleteClinicExam(String id) {
        boolean veterinary = clinicExamRepository.existsClinicExamById(id);
        if(veterinary){
            clinicExamRepository.deleteById(id);
        }else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "id de examen clinico no existe");
        }
    }
}
