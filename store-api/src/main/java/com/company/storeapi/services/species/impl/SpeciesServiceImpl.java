package com.company.storeapi.services.species.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.SpeciesMapper;
import com.company.storeapi.core.util.StandNameUtil;
import com.company.storeapi.model.entity.Species;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.species.RequestAddSpeciesDTO;
import com.company.storeapi.model.payload.request.species.RequestUpdateSpeciesDTO;
import com.company.storeapi.model.payload.response.species.ResponseSpeciesDTO;
import com.company.storeapi.repositories.species.facade.SpeciesRepositoryFacade;
import com.company.storeapi.services.species.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
@RequiredArgsConstructor
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepositoryFacade speciesRepositoryFacade;
    private final SpeciesMapper speciesMapper;

    @Override
    public List<ResponseSpeciesDTO> getAll() {
        List<Species> species = speciesRepositoryFacade.getAll();
        return species.stream().map(speciesMapper::toVeterinaryDto).collect(Collectors.toList());
    }

    @Override
    public ResponseSpeciesDTO validateAndGetById(String id) {
        return speciesMapper.toVeterinaryDto(speciesRepositoryFacade.validateAndGetById(id));
    }

    @Override
    public ResponseSpeciesDTO save(RequestAddSpeciesDTO requestAddSpeciesDTO) {
        String description = StandNameUtil.toCapitalLetters(requestAddSpeciesDTO.getDescription());
        boolean isDescription = speciesRepositoryFacade.existsSpeciesByDescription(description);
        if (isDescription) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la especie con el nombre " + description + " ya existe");
        }
        if (requestAddSpeciesDTO.getDescription().isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La especie no puede estar vacía");
        }

        Species species = new Species();
        species.setDescription(requestAddSpeciesDTO.getDescription());
        species.setCreateAt(new Date());
        species.setStatus(Status.ACTIVO);
        return speciesMapper.toVeterinaryDto(speciesRepositoryFacade.save(species));
    }

    @Override
    public ResponseSpeciesDTO update(RequestUpdateSpeciesDTO requestUpdateSpeciesDTO) {

        Species species = speciesRepositoryFacade.validateAndGetById(requestUpdateSpeciesDTO.getId());
        species.setDescription(defaultIfNull(requestUpdateSpeciesDTO.getDescription(), species.getDescription()));

        return speciesMapper.toVeterinaryDto(speciesRepositoryFacade.save(species));
    }

    @Override
    public void delete(String id) {
        speciesRepositoryFacade.delete(id);
    }
}
