package com.company.storeapi.services.vaccination;

import com.company.storeapi.model.payload.request.vaccination.RequestAddVaccinationDTO;
import com.company.storeapi.model.payload.request.vaccination.RequestUpdateVaccinationDTO;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccinationDTO;

import java.util.List;

public interface VaccinationService {

    List<ResponseVaccinationDTO> getAll();

    ResponseVaccinationDTO validateAndGetById(String id);

    ResponseVaccinationDTO save(RequestAddVaccinationDTO requestAddVaccinationDTO);

    ResponseVaccinationDTO update(RequestUpdateVaccinationDTO requestUpdateVeterinaryDTO);

    void delete(String id);
}
