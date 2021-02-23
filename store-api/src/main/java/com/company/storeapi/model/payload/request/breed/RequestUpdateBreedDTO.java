package com.company.storeapi.model.payload.request.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateBreedDTO {

    private String id;
    @Schema(example = "Doberman")
    private String description;



}
