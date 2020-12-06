package com.company.storeapi.model.payload.response.product;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListProductPaginationDto {

    private int count;
    private List<ResponseProductDTO> products;

}
