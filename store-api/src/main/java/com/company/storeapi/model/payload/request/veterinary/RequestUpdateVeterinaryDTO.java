package com.company.storeapi.model.payload.request.veterinary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateVeterinaryDTO {

    private String id;
    @Schema(example = "Felipe")
    private String name;
    @Schema(example = "Mosquera")
    private String surname;
}
