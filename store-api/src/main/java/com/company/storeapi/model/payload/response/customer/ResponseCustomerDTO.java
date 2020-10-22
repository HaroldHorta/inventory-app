package com.company.storeapi.model.payload.response.customer;

import com.company.storeapi.model.enums.Status;
import lombok.Data;

@Data
public class ResponseCustomerDTO {
    
    private String id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private Status status;
}
