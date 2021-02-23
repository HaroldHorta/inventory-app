package com.company.storeapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "clinicExam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicExam {

    @Id
    private String id;
    private String exam;
    private Date creatAt;

}
