package com.company.storeapi.model.payload.response.breed;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListBreedPaginationDto {

    private int limitMin;
    private int limitMax;
    private int totalData;
    private int size;
    private List<ResponseBreedDTO> breedDTOS;
}
