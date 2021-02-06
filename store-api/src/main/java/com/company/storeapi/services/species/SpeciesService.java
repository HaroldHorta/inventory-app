package com.company.storeapi.services.species;


import com.company.storeapi.model.payload.request.species.RequestAddSpeciesDTO;
import com.company.storeapi.model.payload.request.species.RequestUpdateSpeciesDTO;
import com.company.storeapi.model.payload.response.species.ResponseSpeciesDTO;

import java.util.List;

public interface SpeciesService {

    List<ResponseSpeciesDTO> getAll();

    ResponseSpeciesDTO validateAndGetById(String id);

    ResponseSpeciesDTO save(RequestAddSpeciesDTO requestAddSpeciesDTO);

    ResponseSpeciesDTO update(RequestUpdateSpeciesDTO requestUpdateSpeciesDTO);

    void delete(String id);

}
