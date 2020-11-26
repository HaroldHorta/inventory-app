package com.company.storeapi.model.payload.request.product;

import com.company.storeapi.model.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RequestAddProductDTO {

    @NotNull
    @Schema(example = "Silla")
    private String name;

    @Schema(example = "Silla de madera")
    private String description;

    @Schema()
    private List<Category> categoryId;

    @Schema(example = "21345")
    private double priceBuy;

    @Schema(example = "30000")
    private double priceSell;

    @Schema(example = "2")
    private int unit;

    @Schema(example = "img/java.jpg")
    private String photo;

}
