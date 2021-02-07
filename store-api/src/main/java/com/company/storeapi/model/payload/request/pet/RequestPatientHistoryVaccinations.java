package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.payload.request.clinichistory.RequestVaccination;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestPatientHistoryVaccinations {

    private Set<RequestVaccination> vaccinations = new LinkedHashSet<>();

}
