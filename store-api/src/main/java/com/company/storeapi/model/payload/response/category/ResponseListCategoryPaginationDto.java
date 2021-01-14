package com.company.storeapi.model.payload.response.category;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListCategoryPaginationDto {

    private int limitMin;
    private int limitMax;
    private int totalData;
    private int size;
    private List<ResponseCategoryDTO> categories;
}
