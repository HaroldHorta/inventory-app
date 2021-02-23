package com.company.storeapi.repositories.pet.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Pet;
import com.company.storeapi.repositories.pet.PetRepository;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PetRepositoryFacadeImpl implements PetRepositoryFacade {

    private final PetRepository petRepository;

    public PetRepositoryFacadeImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<Pet> getAllPet() {
        try {
            return Optional.of(petRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de mascotas"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Pet validateAndGetPetById(String id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "mascota con el id: " + id + " no encontrada"));
    }

    @Override
    public Pet savePet(Pet entity) {
        return petRepository.save(entity);

    }

    @Override
    public void deletePet(String id) {

       boolean pet = petRepository.existsPetById(id);
        if(pet){
            petRepository.deleteById(id);
        }else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "id de mascota no existe");
        }


    }

    @Override
    public Boolean existsPetById(String id) {
        return petRepository.existsPetById(id);
    }

    @Override
    public List<Pet> findPetByCustomerNroDocument(String nroDocument) {
        return Optional.of(petRepository.findPetByCustomerNroDocument(nroDocument))
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de mascotas"));
    }

}
