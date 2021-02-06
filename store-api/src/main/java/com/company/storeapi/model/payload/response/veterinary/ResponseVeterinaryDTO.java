package com.company.storeapi.model.payload.response.veterinary;

import lombok.Data;

@Data
public class ResponseVeterinaryDTO {

    private String id;
    private String name;
    private String surname;
    private String createAt;
    private String professionalCard;

}
