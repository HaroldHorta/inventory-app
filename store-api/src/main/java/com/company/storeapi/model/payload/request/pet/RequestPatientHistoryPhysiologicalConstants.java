package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestPatientHistoryPhysiologicalConstants {
    private Set<RequestPhysiologicalConstants> physiologicalConstants = new LinkedHashSet<>();
}
