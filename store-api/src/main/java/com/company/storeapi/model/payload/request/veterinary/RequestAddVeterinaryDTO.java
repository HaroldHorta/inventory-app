package com.company.storeapi.model.payload.request.veterinary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAddVeterinaryDTO {

    @Schema(example = "Arturo")
    private String name;
    @Schema(example = "Mosquera")
    private String surname;
    @Schema(example = "12345")
    private String professionalCard;

}
