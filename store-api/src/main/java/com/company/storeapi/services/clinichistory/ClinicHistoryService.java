package com.company.storeapi.services.clinichistory;

import com.company.storeapi.model.payload.request.clinichistory.RequestAddClinicHistoryDTO;
import com.company.storeapi.model.payload.request.clinichistory.RequestUpdateClinicHistoryDTO;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;

import java.util.List;

public interface ClinicHistoryService {

    List<ResponseClinicHistoryDTO> getClinicHistoryByCustomerNroDocument(String nroDocument);

    ResponseClinicHistoryDTO validateAndGetClinicHistoryById(String id);

    ResponseClinicHistoryDTO saveClinicHistory(RequestAddClinicHistoryDTO requestAddClinicHistoryDTO) ;

    ResponseClinicHistoryDTO updateClinicHistory(RequestUpdateClinicHistoryDTO requestUpdateClinicHistoryDTO) ;

}
