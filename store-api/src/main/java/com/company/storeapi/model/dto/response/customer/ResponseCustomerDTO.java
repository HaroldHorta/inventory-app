package com.company.storeapi.model.dto.response.customer;

import lombok.Data;

@Data
public class ResponseCustomerDTO {
    
    private String id;
    private String name;
    private String email;
    private String address;
    private String phone;
}
