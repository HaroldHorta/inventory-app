package com.company.storeapi.model.payload.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    @Schema(example = "admin_name")
    private String username;

    @NotBlank
    @Schema(example = "password")
    private String password;
}
