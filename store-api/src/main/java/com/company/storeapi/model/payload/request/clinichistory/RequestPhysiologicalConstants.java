package com.company.storeapi.model.payload.request.clinichistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPhysiologicalConstants {
    @Schema(example = "operación corazón")
    private String capillaryFillTime;
    @Schema(example = "operación corazón")
    private String heartRate;
    @Schema(example = "operación corazón")
    private String respiratoryFrequency;
    @Schema(example = "operación corazón")
    private String pulse;
    @Schema(example = "operación corazón")
    private String temperature;
    @Schema(example = "operación corazón")
    private String weight;

}
