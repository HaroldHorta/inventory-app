package com.company.storeapi.model.payload.request.customer;

import com.company.storeapi.model.enums.TypeDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RequestAddCustomerDTO {

    @Schema(example = "Harold Horta")
    @NotBlank
    private String name;

    @Schema()
    @NotBlank
    private TypeDocument typeDocument;

    @Schema(example = "123456789")
    @NotBlank
    private String nroDocument;

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
