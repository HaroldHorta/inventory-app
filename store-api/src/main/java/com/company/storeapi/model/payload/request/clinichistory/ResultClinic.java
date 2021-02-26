package com.company.storeapi.model.payload.request.clinichistory;

import lombok.Data;

@Data
public class ResultClinic {
    private String interpretationResults;
    private String diagnosticImpression;
}
