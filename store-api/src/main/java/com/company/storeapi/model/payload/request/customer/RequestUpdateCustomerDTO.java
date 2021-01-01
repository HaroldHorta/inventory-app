package com.company.storeapi.model.payload.request.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RequestUpdateCustomerDTO {

    @Schema(example = "Andres horta")
    @NotBlank
    private String name;

    @Schema(example = "harold.horta.2@test.com")
    @Email
    private String email;

    @Schema(example = "carrera 123")
    private String address;

    @Schema(example = "1234567890")
    private String phone;
}
