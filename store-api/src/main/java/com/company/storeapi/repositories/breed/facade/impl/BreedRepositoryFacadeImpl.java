package com.company.storeapi.repositories.breed.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.repositories.breed.BreedRepository;
import com.company.storeapi.repositories.breed.facade.BreedRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BreedRepositoryFacadeImpl implements BreedRepositoryFacade {

    private final BreedRepository breedRepository;

    public BreedRepositoryFacadeImpl(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @Override
    public List<Breed> getAllBreed() {
        try {
            return Optional.of(breedRepository.findAll())
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de raza"));
        }catch (EmptyResultDataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public List<Breed> findAllByStatus(Status status, Pageable pageable) {
        try {
            return new ArrayList<>(breedRepository.findAllByStatus(status, pageable));
        }catch (EmptyResultDataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public int countByStatus(Status status) {
        try {
            return breedRepository.countByStatus(status);
        }catch (EmptyResultDataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public Breed validateAndGetBreedById(String id) {

        return  breedRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "Raza con el id: "+ id + " no encontrada" ));

    }

    @Override
    public Breed saveBreed(Breed entity) {
        return breedRepository.save(entity);

    }

    @Override
    public void deleteBreed(String id) {
        Optional<Breed> breed = breedRepository.findById(id);
        if(breed.isPresent()){
            breedRepository.deleteById(id);
        }else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }
    }

    @Override
    public Boolean existsBreedByDescription(String description) {
        return breedRepository.existsBreedByDescription(description);
    }
}
