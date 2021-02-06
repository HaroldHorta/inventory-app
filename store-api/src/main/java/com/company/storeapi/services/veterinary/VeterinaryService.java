package com.company.storeapi.services.veterinary;

import com.company.storeapi.model.payload.request.veterinary.RequestAddVeterinaryDTO;
import com.company.storeapi.model.payload.request.veterinary.RequestUpdateVeterinaryDTO;
import com.company.storeapi.model.payload.response.veterinary.ResponseVeterinaryDTO;

import java.util.List;

public interface VeterinaryService {

    List<ResponseVeterinaryDTO> getAllVeterinary() ;

    ResponseVeterinaryDTO validateAndGetVeterinaryById(String id);

    ResponseVeterinaryDTO saveVeterinary(RequestAddVeterinaryDTO entity) ;

    ResponseVeterinaryDTO updateVeterinary(RequestUpdateVeterinaryDTO requestUpdateVeterinaryDTO) ;

    void deleteVeterinary(String  id) ;

}
