package com.company.storeapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "clinic_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicHistory {

    @Id
    private String id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
    private Veterinary veterinary;
    private Customer customer;
    private Pet pet;
    private String reasonOfConsultation;
    private String anamnesis;

}
