package com.company.storeapi.model.payload.request.clinichistory;

import lombok.Data;

@Data
public class RequestListProblems {

    private String problem;
    private String listMaster;
    private String differentialDiagnosis;
}
