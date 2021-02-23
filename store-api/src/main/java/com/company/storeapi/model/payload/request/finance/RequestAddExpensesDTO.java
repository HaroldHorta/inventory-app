package com.company.storeapi.model.payload.request.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestAddExpensesDTO {

    @Schema(example = "Pago a proveedor de comida para gatos")
    private String description;
    @Schema(example = "175896")
    private double price;
    @Schema
    private Set<String> images = new LinkedHashSet<>();

}
