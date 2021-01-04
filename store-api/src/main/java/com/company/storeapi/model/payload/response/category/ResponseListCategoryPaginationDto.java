package com.company.storeapi.model.payload.response.category;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListCategoryPaginationDto {

    private int count;
    private List<ResponseCategoryDTO> categories;
}
