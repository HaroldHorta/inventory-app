package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.enums.Attitude;
import com.company.storeapi.model.enums.BodyCondition;
import com.company.storeapi.model.enums.StateDehydration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestClinicExamClinicHistory {

    private Attitude attitude;
    private BodyCondition bodyCondition;
    private StateDehydration stateDehydration;
    private Set<RequestAddClinicExamClinicHistory> clinicExamClinicHistories = new LinkedHashSet<>();



}
