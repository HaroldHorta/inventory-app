package com.company.storeapi.model.payload.response.customer;

import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.enums.TypeDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResponseCustomerDTO {
    
    private String id;
    private String name;
    private TypeDocument typeDocument;
    private String nroDocument;
    private String email;
    private String address;
    private String phone;
    private Status status;
}
