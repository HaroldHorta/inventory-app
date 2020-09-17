package com.company.storeapi.model.payload.request.product;

import com.company.storeapi.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestUpdateProductDTO {


    @NotNull
    @Schema(example = "Silla")
    private String name;

    @Schema(example = "Silla de plástico")
    private String description;

    @Schema
    private String categoryId;

    @Schema
    private Status status;

    @Schema(example = "21345")
    private Double priceBuy;

    @Schema(example = "21500")
    private Double priceSell;

    @Schema(example = "1")
    private int unit;
}