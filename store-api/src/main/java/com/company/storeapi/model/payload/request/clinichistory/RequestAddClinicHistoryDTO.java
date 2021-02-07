package com.company.storeapi.model.payload.request.clinichistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class RequestAddClinicHistoryDTO {

    private Date createAt;
    @Schema(example = "601ddd6e1f971d29dcec53de")
    private String veterinary;
    @Schema(example = "601c835df4b9c71f38e3946e")
    private String customer;
    @Schema(example = "601f121b7dccde2c42dc326f")
    private String pet;
    @Schema(example = "dolor abdominal")
    private String reasonOfConsultation;
    @Schema(example = "dolor abdominal")
    private String anamnesis;
    @Schema(example = "dolor abdominal")
    private String recipeBook;


}
