package com.company.storeapi.repositories.veterinary.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Veterinary;
import com.company.storeapi.repositories.veterinary.VeterinaryRepository;
import com.company.storeapi.repositories.veterinary.facade.VeterinaryRepositoryFacade;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class VeterinaryRepositoryFacadeImpl implements VeterinaryRepositoryFacade {

    private final VeterinaryRepository veterinaryRepository;

    public VeterinaryRepositoryFacadeImpl(VeterinaryRepository veterinaryRepository) {
        this.veterinaryRepository = veterinaryRepository;
    }

    @Override
    public List<Veterinary> getAllVeterinary() {
        try {
            return Optional.of(veterinaryRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de razas"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Veterinary validateAndGetVeterinaryById(String id) {
        return veterinaryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "veterinario con el id: " + id + " no encontrada"));
    }

    @Override
    public Veterinary saveVeterinary(Veterinary entity) {
        return veterinaryRepository.save(entity);

    }

    @Override
    public void deleteVeterinary(String id) {
       boolean veterinary = veterinaryRepository.existsVeterinaryById(id);
        if(veterinary){
            veterinaryRepository.deleteById(id);
        }else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "id de veterinario no existe");
        }


    }

    @Override
    public Boolean existsVeterinaryByProfessionalCard(String professionalCard) {
        return veterinaryRepository.existsVeterinaryByProfessionalCard(professionalCard);
    }
}
