package com.company.storeapi.model.payload.response.vaccination;

import com.company.storeapi.model.enums.Status;
import lombok.Data;

@Data
public class ResponseVaccinationDTO {

    private String id;
    private String description;
    private Status status;
    private String createAt;
    private String lot;

}
