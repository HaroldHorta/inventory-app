package com.company.storeapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "diagnostic_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticPlan {

    @Id
    private String id;
    @NotNull
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
