package com.company.storeapi.model.payload.response.vaccination;

import com.company.storeapi.model.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseVaccinationDTO {

    private String id;
    private String description;
    private Status status;
    private Date createAt;
    private String lot;
    private String observation;


}
