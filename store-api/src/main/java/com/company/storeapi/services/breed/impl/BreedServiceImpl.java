package com.company.storeapi.services.breed.impl;


import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.BreedMapper;
import com.company.storeapi.core.util.StandNameUtil;
import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.breed.RequestAddBreedDTO;
import com.company.storeapi.model.payload.request.breed.RequestUpdateBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseListBreedPaginationDto;
import com.company.storeapi.repositories.breed.facade.BreedRepositoryFacade;
import com.company.storeapi.repositories.clinicalhistory.facade.ClinicalHistoryRepositoryFacade;
import com.company.storeapi.services.breed.BreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {

    private final BreedRepositoryFacade repositoryFacade;
    private final ClinicalHistoryRepositoryFacade clinicalHistoryRepositoryFacade;
    private final BreedMapper breedMapper;

    @Value("${spring.size.pagination}")
    private int size;

    @Override
    public List<ResponseBreedDTO> getAllBreed() {
        List<Breed> breeds = repositoryFacade.getAllBreed();
        return breeds.stream().map(breedMapper::toBreedDto).collect(Collectors.toList());
    }

    @Override
    public ResponseBreedDTO validateAndGetBreedById(String id) {
        return breedMapper.toBreedDto(repositoryFacade.validateAndGetBreedById(id));
    }

    @Override
    public ResponseBreedDTO saveBreed(RequestAddBreedDTO requestAddBreedDTO) {
        String description = StandNameUtil.toCapitalLetters(requestAddBreedDTO.getDescription().trim());
        boolean isDescriptionBreed = repositoryFacade.existsBreedByDescription(description);
        if (isDescriptionBreed) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La raza con el nombre " + description + " ya existe");
        }
        if (requestAddBreedDTO.getDescription().isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La raza no puede estar vacia");
        }
        Breed breed = new Breed();
        breed.setDescription(StandNameUtil.toCapitalLetters(requestAddBreedDTO.getDescription().trim()));
        breed.setStatus(Status.ACTIVO);
        breed.setCreateAt(new Date());

        return breedMapper.toBreedDto(repositoryFacade.saveBreed(breed));
    }

    @Override
    public ResponseBreedDTO updateBreed(RequestUpdateBreedDTO requestUpdateBreedDTO) {
        Breed breed = repositoryFacade.validateAndGetBreedById(requestUpdateBreedDTO.getId());
        breedMapper.updateBreedFromDto(requestUpdateBreedDTO, breed);
        return breedMapper.toBreedDto(repositoryFacade.saveBreed(breed));
    }

    @Override
    public void deleteById(String id) {
        boolean clinicHistories = clinicalHistoryRepositoryFacade.existsClinicHistoriesById(id);
        if (!clinicHistories) {
            repositoryFacade.deleteBreed(id);
        } else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La raza esta siendo usada no se puede eliminar");
        }

    }

    @Override
    public ResponseListBreedPaginationDto getBreedPageable() {
        List<Breed> breeds = repositoryFacade.getAllBreed();
        List<ResponseBreedDTO> responseBreedDTOS = breeds.stream().map(breedMapper::toBreedDto).collect(Collectors.toList());

        ResponseListBreedPaginationDto responseListBreedPaginationDto = new ResponseListBreedPaginationDto();
        responseListBreedPaginationDto.setBreedDTOS(responseBreedDTOS);
        responseListBreedPaginationDto.setLimitMax(breeds.size());
        return responseListBreedPaginationDto;
    }

    @Override
    public ResponseListBreedPaginationDto getBreedPageable(Pageable pageable) {
        List<Breed> breeds = repositoryFacade.findAllByStatus(Status.ACTIVO, pageable);
        List<ResponseBreedDTO> responseBreedDTOS = breeds.stream().map(breedMapper::toBreedDto).collect(Collectors.toList());
        int totalData = repositoryFacade.countByStatus(Status.ACTIVO);
        ResponseListBreedPaginationDto responseListBreedPaginationDto = new ResponseListBreedPaginationDto();
        responseListBreedPaginationDto.setBreedDTOS(responseBreedDTOS);

        int limitMin = getLimit(pageable, 1, (pageable.getPageNumber() * size) + 1);

        int limitMax = getLimit(pageable, size, (pageable.getPageNumber() + 1) * size);

        responseListBreedPaginationDto.setLimitMin(limitMin);
        responseListBreedPaginationDto.setLimitMax(Math.min(totalData, limitMax));
        responseListBreedPaginationDto.setTotalData(totalData);
        responseListBreedPaginationDto.setSize(size);
        return responseListBreedPaginationDto;
    }

    private int getLimit(Pageable pageable, int i, int i2) {
        return StandNameUtil.getLimitPaginator(pageable, i, i2);
    }


}
