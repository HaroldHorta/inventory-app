package com.company.storeapi.model.payload.request.clinichistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestVaccination {

    @Schema(example = "601dfe2c718c722127fbd28e")
    private String id;
    private Date vaccinationDate;
}
