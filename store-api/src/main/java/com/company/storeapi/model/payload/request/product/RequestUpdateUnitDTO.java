package com.company.storeapi.model.payload.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestUpdateUnitDTO {
    @Schema(example= "abdawdvwubafwbu122j")
    private String id;

    @Schema(example = "21345")
    private Double priceBuy;

    @Schema(example = "21500")
    private Double priceSell;

    @Schema(example = "1")
    private Integer unit;
}
