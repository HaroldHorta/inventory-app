package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccination;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestPatientHistoryVaccinations {

    private Set<ResponseVaccination> vaccinations = new LinkedHashSet<>();
    private RequestPhysiologicalConstants physiologicalConstants;

}
