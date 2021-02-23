package com.company.storeapi.model.payload.response.customer;


import lombok.Data;

import java.util.List;

@Data
public class ResponseListCustomerPaginationDto {

    private int limitMin;
    private int limitMax;
    private int totalData;
    private int size;
    private List<ResponseCustomerDTO> customers;

}
