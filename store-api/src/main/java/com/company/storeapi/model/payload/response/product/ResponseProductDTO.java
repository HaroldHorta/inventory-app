package com.company.storeapi.model.payload.response.product;

import com.company.storeapi.model.payload.request.user.FileInfo;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.enums.Status;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class ResponseProductDTO {

    private String id;

    private String name;

    private String description;

    private Set<ResponseCategoryDTO> category;

    private Status status;

    private String createAt;

    private String updateAt;

    private Double priceBuy;

    private Double priceSell;

    private int unit;

    private String photo;

}
