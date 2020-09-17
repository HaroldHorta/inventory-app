package com.company.storeapi.model.payload.request.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RequestAddCustomerDTO {

    @Schema(example = "Harold Horta")
    @NotBlank
    private String name;

    @Schema(example = "harold.horta@test.com")
    @NotBlank
    @Email
    private String email;

    @Schema(example = "calle 123")
    @NotBlank
    private String address;

    @Schema(example = "44556677")
    @NotBlank
    private String phone;
}
