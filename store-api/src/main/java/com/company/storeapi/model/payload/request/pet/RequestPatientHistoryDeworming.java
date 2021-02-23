package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import lombok.Data;

@Data
public class RequestPatientHistoryDeworming {
    private RequestDeworming deworming;
    private RequestPhysiologicalConstants physiologicalConstants;

}
