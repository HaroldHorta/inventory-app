package com.company.storeapi.model.payload.request.pet;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestPatientHistoryDeworming {
    private Set<RequestDeworming> deworming = new LinkedHashSet<>();
}
