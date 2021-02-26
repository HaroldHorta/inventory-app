package com.company.storeapi.services.clinichistory;

import com.company.storeapi.model.payload.request.clinichistory.*;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;

import java.util.List;

public interface ClinicHistoryService {

    ResponseClinicHistoryDTO validateAndGetClinicHistoryById(String id);

    ResponseClinicHistoryDTO saveClinicHistory(RequestAddClinicHistoryDTO requestAddClinicHistoryDTO);

    ResponseClinicHistoryDTO updateDiagnosticPlanClinicHistory(String id, RequestDiagnosticPlan requestDiagnosticPlan);

    ResponseClinicHistoryDTO updateResultClinicHistory(String id, ResultClinic resultClinic);

    ResponseClinicHistoryDTO updateTherapeuticPlan(String id, RequestTherapeuticPlan requestTherapeuticPlan);

    List<ResponseClinicHistoryDTO> findClinicHistoryByCustomer(String nroDocument);

}
