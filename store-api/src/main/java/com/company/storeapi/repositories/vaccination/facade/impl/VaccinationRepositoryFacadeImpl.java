package com.company.storeapi.repositories.vaccination.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Vaccination;
import com.company.storeapi.repositories.vaccination.VaccinationRepository;
import com.company.storeapi.repositories.vaccination.facade.VaccinationRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class VaccinationRepositoryFacadeImpl implements VaccinationRepositoryFacade {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationRepositoryFacadeImpl(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @Override
    public List<Vaccination> getAll() {
        try {
            return Optional.of(vaccinationRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de vacunas"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Vaccination validateAndGetById(String id) {
        return vaccinationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "Vacuna con el id: " + id + " no encontrada"));
    }

    @Override
    public Vaccination save(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    @Override
    public void delete(String id) {
        boolean vaccination = vaccinationRepository.existsVaccinationById(id);
        if (vaccination) {
            vaccinationRepository.deleteById(id);
        } else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "La vacuna no existe");
        }
    }

    @Override
    public boolean existsVaccinationByDescription(String description) {
        return vaccinationRepository.existsVaccinationByDescription(description);
    }
}
