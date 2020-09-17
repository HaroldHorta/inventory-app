package com.company.storeapi.model.payload.response.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ResponseOrderProductItemsDTO {

    @Schema(example = "15")
    @NotNull
    private String id;

    @Schema(example = "1")
    @NotNull
    @Positive
    private Integer unit;

    private Double total;

}
