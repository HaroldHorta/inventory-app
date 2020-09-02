package com.company.storeapi.model.dto.response.product;

import com.company.storeapi.model.dto.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.enums.Status;
import lombok.Data;


@Data
public class ResponseProductDTO {

    private String id;

    private String name;

    private String description;

    private ResponseCategoryDTO category;

    private Status status;

    private String createAt;

    private String updateAt;

    private Double priceBuy;

    private Double priceSell;

    private int unit;

}
