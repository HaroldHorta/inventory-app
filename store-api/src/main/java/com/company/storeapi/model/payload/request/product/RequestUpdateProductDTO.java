package com.company.storeapi.model.payload.request.product;

import com.company.storeapi.model.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RequestUpdateProductDTO {


    @NotNull
    @Schema(example = "Silla")
    private String name;

    @Schema(example = "Silla de plástico")
    private String description;

    @Schema
    private List<Category> categoryId;

    @Schema(example = "21345")
    private Double priceBuy;

    @Schema(example = "21500")
    private Double priceSell;

}