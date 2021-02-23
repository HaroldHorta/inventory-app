package com.company.storeapi.model.payload.request.product;

import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class RequestOrderProductItemsDTO {

    @Schema(example = "5f4d93ab0d70a908c47d3045")
    @NotNull
    private ResponseProductDTO product;

    @Schema(example = "1")
    @NotNull
    @Positive
    private Integer unit;

}
