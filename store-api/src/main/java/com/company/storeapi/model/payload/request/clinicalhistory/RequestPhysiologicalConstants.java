package com.company.storeapi.model.payload.request.clinicalhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPhysiologicalConstants {

    private String  capillaryFillTime;
    private String heartRate;
    private String respiratoryFrequency;
    private String pulse;
    private String temperature;
    private String weight;

}
