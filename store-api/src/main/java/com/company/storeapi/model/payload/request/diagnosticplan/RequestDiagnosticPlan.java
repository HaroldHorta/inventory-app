package com.company.storeapi.model.payload.request.diagnosticplan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestDiagnosticPlan {
    @Schema(example = "Cuadro  Hemático")
    private String description;
}
