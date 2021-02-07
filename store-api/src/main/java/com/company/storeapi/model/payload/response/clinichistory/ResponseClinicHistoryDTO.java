package com.company.storeapi.model.payload.response.clinichistory;

import com.company.storeapi.model.entity.*;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseClinicHistoryDTO {

    private String id;
    private Date createAt;
    private Veterinary veterinary;
    private Customer customer;
    private Pet pet;
    private String reasonOfConsultation;
    private String anamnesis;

}
