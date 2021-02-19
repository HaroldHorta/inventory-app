package com.company.storeapi.model.payload.request.clinichistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class RequestAddClinicHistoryDTO {

    private String veterinary;
    @Schema(example = "601f121b7dccde2c42dc326f")
    private String pet;
    @Schema(example = "dolor abdominal")
    private String reasonOfConsultation;
    @Schema(example = "dolor abdominal")
    private String anamnesis;
    @Schema(example = "dolor abdominal")
    private String recipeBook;
    private RequestPhysiologicalConstants physiologicalConstants;
    private RequestClinicExamClinicHistory clinicExam;




}
