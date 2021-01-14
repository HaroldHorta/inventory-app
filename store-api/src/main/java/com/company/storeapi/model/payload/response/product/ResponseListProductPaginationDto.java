package com.company.storeapi.model.payload.response.product;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListProductPaginationDto {

    private int limitMin;
    private int limitMax;
    private int totalData;
    private int size;
    private List<ResponseProductDTO> products;

}
