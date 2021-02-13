package com.company.storeapi.services.veterinary.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.VeterinaryMapper;
import com.company.storeapi.core.util.Util;
import com.company.storeapi.model.entity.Veterinary;
import com.company.storeapi.model.payload.request.veterinary.RequestAddVeterinaryDTO;
import com.company.storeapi.model.payload.request.veterinary.RequestUpdateVeterinaryDTO;
import com.company.storeapi.model.payload.response.veterinary.ResponseVeterinaryDTO;
import com.company.storeapi.repositories.veterinary.facade.VeterinaryRepositoryFacade;
import com.company.storeapi.services.veterinary.VeterinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
@RequiredArgsConstructor
public class VeterinaryServiceImpl implements VeterinaryService {

    private final VeterinaryRepositoryFacade veterinaryRepositoryFacade;
    private final VeterinaryMapper veterinaryMapper;

    @Override
    public List<ResponseVeterinaryDTO> getAllVeterinary() {
        List<Veterinary> veterinaries = veterinaryRepositoryFacade.getAllVeterinary();
        return veterinaries.stream().map(veterinaryMapper::toVeterinaryDto).collect(Collectors.toList());
    }

    @Override
    public ResponseVeterinaryDTO validateAndGetVeterinaryById(String id) {
        return veterinaryMapper.toVeterinaryDto(veterinaryRepositoryFacade.validateAndGetVeterinaryById(id));
    }

    @Override
    public ResponseVeterinaryDTO saveVeterinary(RequestAddVeterinaryDTO requestAddVeterinaryDTO) {
        String professionalCard = Util.toCapitalLetters(requestAddVeterinaryDTO.getProfessionalCard().trim());
        boolean isProfessionalCard = veterinaryRepositoryFacade.existsVeterinaryByProfessionalCard(professionalCard);

        if (isProfessionalCard) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "el veterinario con el numero " + professionalCard + " ya existe");
        }
        if (requestAddVeterinaryDTO.getName().isEmpty() || requestAddVeterinaryDTO.getSurname().isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "el nombre y apellido no pueden estar vacia");
        }

        Veterinary veterinary = new Veterinary();
        veterinary.setName(requestAddVeterinaryDTO.getName());
        veterinary.setSurname(requestAddVeterinaryDTO.getSurname());
        veterinary.setCreateAt(new Date());
        veterinary.setProfessionalCard(requestAddVeterinaryDTO.getProfessionalCard());
        return veterinaryMapper.toVeterinaryDto(veterinaryRepositoryFacade.saveVeterinary(veterinary));
    }

    @Override
    public ResponseVeterinaryDTO updateVeterinary(RequestUpdateVeterinaryDTO requestUpdateVeterinaryDTO) {

        Veterinary veterinary = veterinaryRepositoryFacade.validateAndGetVeterinaryById(requestUpdateVeterinaryDTO.getId());
        veterinary.setName(defaultIfNull(requestUpdateVeterinaryDTO.getName().trim(), veterinary.getName()));
        veterinary.setSurname(defaultIfNull(requestUpdateVeterinaryDTO.getSurname().trim(), veterinary.getSurname()));

        return veterinaryMapper.toVeterinaryDto(veterinaryRepositoryFacade.saveVeterinary(veterinary));
    }

    @Override
    public void deleteVeterinary(String id) {
        veterinaryRepositoryFacade.deleteVeterinary(id);
    }
}
