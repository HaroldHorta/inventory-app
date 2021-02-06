package com.company.storeapi.services.vaccination.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.VaccinationMapper;
import com.company.storeapi.core.util.StandNameUtil;
import com.company.storeapi.model.entity.Vaccination;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.vaccination.RequestAddVaccinationDTO;
import com.company.storeapi.model.payload.request.vaccination.RequestUpdateVaccinationDTO;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccinationDTO;
import com.company.storeapi.repositories.vaccination.facade.VaccinationRepositoryFacade;
import com.company.storeapi.services.vaccination.VaccinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
@RequiredArgsConstructor
public class VaccinationServiceImpl implements VaccinationService {

    private final VaccinationRepositoryFacade vaccinationRepositoryFacade;
    private final VaccinationMapper vaccinationMapper;

    @Override
    public List<ResponseVaccinationDTO> getAll() {
        List<Vaccination> vaccinations = vaccinationRepositoryFacade.getAll();
        return vaccinations.stream().map(vaccinationMapper::toVaccinationDto).collect(Collectors.toList());
    }

    @Override
    public ResponseVaccinationDTO validateAndGetById(String id) {
        return vaccinationMapper.toVaccinationDto(vaccinationRepositoryFacade.validateAndGetById(id));
    }

    @Override
    public ResponseVaccinationDTO save(RequestAddVaccinationDTO requestAddVaccinationDTO) {
        String description = StandNameUtil.toCapitalLetters(requestAddVaccinationDTO.getDescription());
        boolean isDescription = vaccinationRepositoryFacade.existsVaccinationByDescription(description);
        if (isDescription) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la vacuna con el nombre " + description + " ya existe");
        }
        if (requestAddVaccinationDTO.getDescription().isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La vacuna no puede estar vacía");
        }

        Vaccination vaccination = new Vaccination();
        vaccination.setDescription(requestAddVaccinationDTO.getDescription());
        vaccination.setCreateAt(new Date());
        vaccination.setStatus(Status.ACTIVO);
        return vaccinationMapper.toVaccinationDto(vaccinationRepositoryFacade.save(vaccination));
    }

    @Override
    public ResponseVaccinationDTO update(RequestUpdateVaccinationDTO requestUpdateVaccinationDTO) {

        Vaccination vaccination = vaccinationRepositoryFacade.validateAndGetById(requestUpdateVaccinationDTO.getId());
        vaccination.setDescription(defaultIfNull(requestUpdateVaccinationDTO.getDescription(), vaccination.getDescription()));

        return vaccinationMapper.toVaccinationDto(vaccinationRepositoryFacade.save(vaccination));
    }

    @Override
    public void delete(String id) {
        vaccinationRepositoryFacade.delete(id);
    }
}
