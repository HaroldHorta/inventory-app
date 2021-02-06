package com.company.storeapi.repositories.species.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Species;
import com.company.storeapi.repositories.species.SpeciesRepository;
import com.company.storeapi.repositories.species.facade.SpeciesRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SpeciesRepositoryFacadeImpl implements SpeciesRepositoryFacade {

    private final SpeciesRepository speciesRepository;

    public SpeciesRepositoryFacadeImpl(SpeciesRepository breedRepository) {
        this.speciesRepository = breedRepository;
    }

    @Override
    public List<Species> getAll() {
        try {
            return Optional.of(speciesRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de especies"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Species validateAndGetById(String id) {

        return speciesRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "Raza con el id: " + id + " no encontrada"));

    }

    @Override
    public Species save(Species species) {
        return speciesRepository.save(species);

    }

    @Override
    public void delete(String id) {
        boolean species = speciesRepository.existsSpeciesById(id);
        if (species) {
            speciesRepository.deleteById(id);
        } else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "La especie no existe");
        }
    }

    @Override
    public boolean existsSpeciesByDescription(String description) {
        return speciesRepository.existsSpeciesByDescription(description);
    }

}
