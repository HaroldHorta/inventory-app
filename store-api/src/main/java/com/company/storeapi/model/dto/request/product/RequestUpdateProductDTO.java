package com.company.storeapi.model.dto.request.product;

import com.company.storeapi.model.dto.request.category.RequestAddCategoryDTO;
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
    private RequestAddCategoryDTO categoryId;

    @Schema
    private Status status;

    @Schema(example = "21345")
    private Double priceBuy;

    @Schema(example = "21500")
    private Double priceSell;
}
