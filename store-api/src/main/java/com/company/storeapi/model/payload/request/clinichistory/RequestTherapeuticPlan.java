package com.company.storeapi.model.payload.request.clinichistory;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RequestTherapeuticPlan {
    Set<TherapeuticPlan> therapeuticPlans = new HashSet<>();
}
