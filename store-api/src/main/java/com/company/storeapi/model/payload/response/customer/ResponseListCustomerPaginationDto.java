package com.company.storeapi.model.payload.response.customer;


import lombok.Data;

import java.util.List;

@Data
public class ResponseListCustomerPaginationDto {

    private int count;
    private List<ResponseCustomerDTO> customers;

}
