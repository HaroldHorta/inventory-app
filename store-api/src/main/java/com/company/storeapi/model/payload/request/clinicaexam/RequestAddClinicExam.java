package com.company.storeapi.model.payload.request.clinicaexam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAddClinicExam {

    @Schema(example = "Ojos")
    private String exam;

}
