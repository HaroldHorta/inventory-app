package com.company.storeapi.services.breed;

import com.company.storeapi.model.payload.request.breed.RequestAddBreedDTO;
import com.company.storeapi.model.payload.request.breed.RequestUpdateBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseListBreedPaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BreedService {

    List<ResponseBreedDTO> getAllBreed() ;

    ResponseBreedDTO validateAndGetBreedById(String id);

    ResponseBreedDTO saveBreed(RequestAddBreedDTO requestAddBreedDTO) ;

    ResponseBreedDTO updateBreed( RequestUpdateBreedDTO requestUpdateBreedDTO) ;

    void deleteById(String id) ;

    ResponseListBreedPaginationDto getBreedPageable();

    ResponseListBreedPaginationDto getBreedPageable(Pageable pageable);
}
