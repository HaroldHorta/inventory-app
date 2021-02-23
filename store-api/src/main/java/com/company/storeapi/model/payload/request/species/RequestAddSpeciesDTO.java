package com.company.storeapi.model.payload.request.species;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAddSpeciesDTO {

    @Schema(example = "Canino")
    private String description;

}
