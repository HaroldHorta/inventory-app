package com.company.storeapi.model.payload.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestAddProductDTO {

    @NotNull
    @Schema(example = "Silla")
    private String name;

    @Schema(example = "Silla de madera")
    private String description;

    @Schema(example = "5f4143c5498c761de44e7426")
    private String categoryId;

    @Schema(example = "21345")
    private Double priceBuy;

    @Schema(example = "30000")
    private Double priceSell;

    @Schema(example = "2")
    private int unit;

}
